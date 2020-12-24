<template>
  <div>
    <van-cell-group>
      <van-field label="新密码" v-model="password" type="password" placeholder="请输入新密码" />
      <van-field label="确认密码" v-model="password2" type="password" placeholder="请确认新密码" />

      <van-field label="验证码" v-model="code" @click-icon="getCode" placeholder="请输入验证码">
        <template #button>
          <van-button v-if="!cutDownTime" size="small" type="primary" @click="getCode">发送验证码</van-button>
          <van-button v-if="cutDownTime" class="cutDownTime" size="small" type="primary">{{cutDownTime}}s后再试</van-button>
        </template>
      </van-field>
    </van-cell-group>

    <div class="bottom_btn">
      <van-button size="large" type="danger" @click="modifypassword">保存</van-button>
    </div>
  </div>
</template>


<script>
import { authCaptcha, authReset, authLogout } from '@/api/api';
import { removeLocalStorage } from '@/utils/local-storage';
import { Field } from 'vant';

export default {
  data: () => ({
    password: '',
    password2: '',
    mobile: '',
    code: '',
    cutDownTime: 0
  }),
  mounted: function() {
    this.mobile = this.$route.query.mobile;
  },

  methods: {
    modifypassword() {
      if (this.passwordValid()) {
        authReset({
          password: this.password,
          mobile: this.mobile,
          code: this.code
        })
          .then(() => {
            this.$dialog.alert({ message: '保存成功, 请重新登录.' });
            logout();
          })
          .catch(error => {
            this.$toast.fail(error || '');
          });
      }
      this.logout();
    },
    logout() {
      authLogout();
      removeLocalStorage('Authorization', 'avatar', 'nickName');
    },
    passwordValid() {
      if (this.password != this.password2) {
        this.$toast.fail('两次密码输入不一致');
        return false;
      }
      return true;
    },
    getCode() {
      if (this.mobile === '') {
        this.$toast.fail('请输入号码');
        return;
      }

      if (!this.cutDownTime) {
        authCaptcha({
          mobile: this.mobile,
          type: 'change-password'
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
            this.counting = 0;
          });
      }
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
