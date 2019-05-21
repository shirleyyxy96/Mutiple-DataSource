<template>
<div class="user">
  <Modal></Modal>
  <Modal v-model="modalUser" title="确认框" @on-ok="submit">
        <p style="font-size:15px">确认{{message}}？</p>
  </Modal>
  <Modal v-model="modalCancel"
        title="确认框"
        @on-ok="cancel">
        <p>确认放弃本页面？</p>
  </Modal>
    <title-page v-if="this.$route.params.id === undefined" bigTitle="添加" smallTitle="添加人物资料"></title-page>
    <title-page v-else bigTitle="修改" smallTitle="修人物资料"></title-page>
    <div class="container">
    <div class="color-style">
      <h3>基本信息</h3>
      <hr></hr>
      <form @submit.prevent="submitComfirm()">
        <label>姓名</label>
        <input class="h_wstyle_user" type="text" v-if="this.$route.params.id"  v-model.trim="user.userName" disabled="disabled" />
        <input class="h_wstyle_user" v-else  v-model.trim="user.userName"/>
        <br/><br/>
        <label>性别</label>
        <Select style="width:202px;height:32px" v-model="user.gender">
          <Option v-for="key,value in genders" v-if="value != 'unlimited'" :value="value" :key="key">{{ key }}</Option>
        </Select>
        <br/><br/>
        <div class="birthdate-div">
        <label class="label-style text-right">出生日期</label>
        <datepicker  v-model="user.birthDate" min="1900-1-1" :max="now"></datepicker>
        </div>
        <br/><br/>
        <label>籍贯</label>
        <input class="h_wstyle_user" type='text' v-model.trim='user.nativePlace' />
        <br/><br/>
        <label>民族</label>
        <Select style="width:202px;height:32px;" v-model="user.nationality">
          <Option v-for="value in nations" :value="value" :key="value">{{ value }}</Option>
        </Select>
        <br/><br/>
        <label>职务</label>
        <input type="text" class="h_wstyle_user"  v-model.trim="user.position" />
        <br/><br/>
      </form>
      <hr></hr>
      <button class="button-style btn-success" @click="submitComfirm()">保存</button>
      <button class="button-style" @click.prevent="comfirmCancel">取消</button>
    </div>
  </div>
</div>
</template>
<script>
import datepicker from 'vue-date';
import titlePage from '../components/Title';
import constants from '../constants/constans';

export default {
  name: 'user',
  data() {
    return {
      now: `${new Date().getFullYear()}-${new Date().getMonth() + 1}-${new Date().getDate()}`,
      genders: constants.genders,
      modalUser: false,
      modalCancel: false,
      message: '',
      nations: constants.nations,
      user: {
        userName: '',
        gender: 'male',
        birthDate: null,
        nativePlace: '',
        nationality: '汉族',
        position: '',
      },
      userSend: {
        userName: '',
        gender: 'male',
        birthDate: null,
        nativePlace: '',
        nationality: '汉族',
        position: '',
      },
    };
  },
  components: {
    datepicker,
    titlePage,
  },
  methods: {
    valueTrim(value) {
      return value.trim();
    },
    comfirmCancel() {
      this.modalCancel = true;
    },
    cancel() {
      this.$router.push('/');
    },
    hasNullValue(obj) {
      let times = 0;
      Object.keys(obj).forEach((key) => {
        if (obj[key] == null || obj[key] === '') {
          if (key !== 'id' && key !== 'oid') {
            times += 1;
          }
        }
      });
      return times;
    },
    submitComfirm() {
      // this.userSend.userName = this.user.userName;
      // this.userSend.gender = this.user.gender;
      // this.userSend.birthDate = this.user.birthDate;
      // this.userSend.nativePlace = this.user.nativePlace;
      // this.userSend.nationality = this.user.nationality;
      // this.userSend.position = this.user.position;
      // this.userSend.userName = this.valueTrim(this.userSend.userName);
      // this.userSend.nativePlace = this.valueTrim(this.userSend.nativePlace);
      // this.userSend.position = this.valueTrim(this.userSend.position);
      if (this.hasNullValue(this.user) > 0) {
        this.$Modal.warning({
          content: '请填满每项信息',
        });
        return;
      }
      if (this.$route.params.id !== undefined) {
        this.message = '修改';
      } else {
        this.message = '保存';
      }
      this.modalUser = true;
    },
    submit() {
      let url = 'create';
      let methodNew = 'post';
      if (this.$route.params.id !== undefined) {
        url = 'update';
        methodNew = 'put';
      }
      this.$axios({
        method: methodNew,
        url: `/users/${url}`,
        data: this.user,
        requestHeader: {
          'Content-Type': 'application/json',
        },
      }).then(() => {
        this.$Message.success({
          content: `${this.message}成功`,
          top: 50,
          duration: 3,
        });
        this.$router.push('/');
      }).catch((error) => {
        this.$Modal.error({
          content: error.message,
        });
      });
    },
    getUser() {
      this.$axios({
        method: 'get',
        url: `/users/${this.$route.params.id}`,
      }).then((response) => {
        this.user = response.data;
      }).catch((error) => {
        this.$Modal.error({
          content: error.message,
        });
      });
    },
  },
  created() {
    if (this.$route.params.id !== undefined) {
      this.getUser(this.$route.params.id);
    }
  },
};
</script>
<style scoped lang='scss'>
@import "../assets/scss/user.scss";
</style>
