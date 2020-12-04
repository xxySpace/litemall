<template>
  <div>
    <van-cell-group>
      <van-field label="登录密码" v-model="password" type="password" placeholder="请输入登录密码" />

      <van-field label="新手机号" v-model="mobile" placeholder="请输入新手机号" />

      <van-field label="验证码" v-model="code" @click-icon="getCode" placeholder="请输入验证码">
        <template #button>
          <van-button v-if="!cutDownTime" size="small" type="primary" @click="getCode">发送验证码</van-button>
          <van-button
            v-if="cutDownTime"
            class="cutDownTime"
            size="small"
            type="primary"
          >{{cutDownTime}}s后再试</van-button>
        </template>
      </van-field>
    </van-cell-group>

    <div class="bottom_btn">
      <van-button size="large" type="danger" @click="saveMobile">保存</van-button>
    </div>
  </div>
</template>


<script>
import { authCaptcha, authResetPhone } from '@/api/api';

import { Field } from 'vant';

export default {
  data: () => ({
    password: '',
    mobile: '',
    code: '',
    cutDownTime: 0
  }),

  methods: {
    getCode() {
      if (!this.cutDownTime && this.vuelidate()) {
        authCaptcha({
          mobile: this.mobile,
          type: 'bind-mobile'
        })
          .then(() => {
            this.$toast.success('发送成功');
            this.cutDownTime = 60;
            let timer = setInterval(() => {
              this.cutDownTime--;
              if (this.cutDownTime <= 0) {
                this.cutDownTime = '';
              }
            }, 1000);
          })
          .catch(error => {
            this.$toast.fail(error.data.errmsg);
            this.cutDownTime = 0;
          });
      }
    },
    vuelidate() {
      if (this.mobile === '') {
        this.$toast.fail('请输入号码');
        return false;
      }
      return true;
    },
    saveMobile() {
      authResetPhone({
        password: this.password,
        mobile: this.mobile,
        code: this.code
      })
        .then(() => {
          this.$dialog.alert({ message: '手机号修改成功' });
          this.$router.go(-1);
        })
        .catch(error => {
          this.$toast.fail(error.data.errmsg || '');
        });
    }
  },

  components: {
    [Field.name]: Field
  }
};
</script>

<style lang="scss" scoped>
@import '../../../../assets/scss/var';
@import '../../../../assets/scss/mixin';
.bottom_btn {
  padding: 30px 15px 0 15px;
}

.cutDownTime {
  color: $font-color-gray;
}
</style>
