<template>
  <div class="user_footprint">
    <van-nav-bar title="我的足迹" left-text="返回" left-arrow @click-left="goback" />
    <van-list
      v-model="loading"
      :finished="finished"
      :immediate-check="false"
      finished-text="没有更多了"
      @load="getFootprintList"
    >
      <div v-for="(data, d) in list" :key=" d">
        <div class="time">
          <span>{{data.addtime}}</span>
        </div>
        <van-card
          v-for="(item, i) in data.subList"
          :key="i"
          :desc="item.brief"
          :title="item.name"
          :thumb="item.picUrl"
          :price="item.retailPrice +'.00'"
          @click="itemClick(item.goodsId)"
        >
          <div slot="footer">
            <van-button
              size="mini"
              icon="star-o"
              @click.stop="addCollect(i, item)"
              :color="(item.userHasCollect !== 0) ? '#FF0000':''"
              plain
            >收藏</van-button>
            <van-button
              size="mini"
              icon="lajitong"
              @click.stop="footprintDel(d,i,item,data.addtime)"
            >删除</van-button>
          </div>
        </van-card>
      </div>
    </van-list>
  </div>
</template>

<script>
import { footprintList, footprintDelete, collectAddOrDelete } from '@/api/api';
import { Card, List, NavBar } from 'vant';
import scrollFixed from '@/mixin/scroll-fixed';

export default {
  mixins: [scrollFixed],

  data() {
    return {
      list: [],
      page: 0,
      limit: 20,
      loading: false,
      finished: false
    };
  },

  created() {
    this.init();
  },

  methods: {
    goback() {
      this.$router.go(-1);
    },
    init() {
      this.page = 0;
      this.list = [];
      this.getFootprintList();
    },
    getFootprintList() {
      this.page++;
      footprintList({ page: this.page, limit: this.limit }).then(res => {
        let dataAll = res.data.data.list;
        let newArr = [];
        dataAll.forEach((item, i) => {
          let index = -1;
          //然后在跑到这里筛选 根据不同的时间放置不同的数组    some（）用来查找数组中是否存在某个值  如果存在 就return true
          let isExists = newArr.some((newItem, j) => {
            if (item.addTime.split(' ')[0] == newItem.addtime) {
              index = j;
              return true;
            }
          });
          //代码是先跑这里的if条件判读
          if (!isExists) {
            newArr.push({
              addtime: item.addTime.split(' ')[0],
              subList: [item]
            });
          } else {
            //商品存在则不重复显示
            let isGoodExists = newArr[index].subList.some((lst, k) => {
              if (item.goodsId == lst.goodsId) {
                return true;
              }
            });
            if (!isGoodExists) {
              newArr[index].subList.push(item);
            }
          }
        });
        if (this.list.length > 0) {
          this.list.push(newArr);
        } else {
          this.list = newArr;
        }
        this.loading = false;
        this.finished = res.data.data.page >= res.data.data.pages;
      });
    },
    addCollect(i, item) {
      collectAddOrDelete({ valueId: item.goodsId, type: 0 }).then(res => {
        if (this.list[0].subList[i].userHasCollect > 0) {
          this.list[0].subList[i].userHasCollect = 0;
        } else {
          this.list[0].subList[i].userHasCollect = 1;
          this.$toast({
            message: '收藏成功',
            duration: 1500
          });
        }
      });
    },
    footprintDel(d, i, item, time) {
      this.$dialog
        .confirm({ message: '是否删除该商品的浏览足迹' })
        .then(() => {
          footprintDelete({ id: item.goodsId, addTime: time }).then(res => {
            this.list[d].subList.splice(i, 1);
            if (this.list[d].subList.length < 1) {
              this.list.splice(d, 1);
            }
            this.$toast({
              message: '删除成功',
              duration: 1500
            });
          });
        })
        .catch(function() {
          // 点击取消执行
          console.log('Clicked on cancel');
        });
    },
    itemClick(id) {
      this.$router.push(`/items/detail/${id}`);
    }
  },

  components: {
    [NavBar.name]: NavBar,
    [List.name]: List,
    [Card.name]: Card
  }
};
</script>

<style lang="scss" scoped>
.clear_invalid {
  width: 120px;
  color: $font-color-gray;
  border: 1px solid $font-color-gray;
  margin: 0 auto;
  text-align: center;
  padding: 5px 3px;
  margin-top: 20px;
  border-radius: 3px;
}
.time {
  font-size: 15px;
  margin-left: 8px;
  margin-top: 8px;
}
</style>
