<template>
<div class="index">
  <Modal></Modal>
  <Modal v-model="modalDel" width="360" >
    <p class="header-modal" slot="header" >
      <Icon type="information-circled"></Icon>
      <span>删除确认</span>
    </p>
    <div class="text-modal">
      <p>是否继续删除？</p>
    </div>
    <div slot="footer">
      <Button type="error" size="large" long :loading="modal_loading" @click="delUsers">删除</Button>
    </div>
  </Modal>
  <Modal v-model="modalDels" width="360">
    <p slot="header" class="header-modal">
      <Icon type="information-circled"></Icon>
      <span>删除确认</span>
    </p>
    <div class="text-modal">
    <p>是否继续删除？</p>
   </div>
   <div slot="footer">
     <Button type="error" size="large" long :loading="modal_loading" @click="delUsers">删除</Button>
   </div>
  </Modal>
    <titlePage bigTitle="人物" smallTitle="人物画像数据库"></titlePage>
    <div class="container">
    <div class="color-style">
      <form>
        <div class="row pad-style">
          <div class="col-md-10 col-lg-10">
            <span>姓名</span>
            <input class="hwstyle" sliceString=5 type="text" v-model="userQuery.userName"/>
            <span>性别</span>
            <Select style="width:8%;" v-model="userQuery.gender">
              <Option v-for="key,value in genders" :value="value" :key="key">{{ key }}</Option>
            </Select>
            <span>职位</span>
            <input class="hwstyle" type="text" v-model="userQuery.position"/>
            <span>出生日期</span>
            <div class="date-div" >
            <datepicker  min="1900-1-1" :max="now"  v-model="userQuery.startDate"></datepicker>
            <span>到</span>
            <datepicker  v-model="userQuery.endDate" :max="now" min="1900-1-1" ></datepicker>
            </div>
          </div>
          <div class="col-md-2 col-lg-2 text-right div-float">
            <button type="button" class="btn btn-primary hw_button" @click="getUsersList(0)">
              <span class="glyphicon glyphicon-search"></span>搜索
            </button>
          </div>
        </div>
      </form>
      <hr class="top-hr"></hr>
      <div class="row pad-style">
        <div class="col-md-8 "></div>
        <div class="col-md-4 text-right">
          <router-link to="/user" tag="button" class="btn btn-success hw_button">
            <span class="glyphicon glyphicon-plus"></span> 添加
          </router-link>
          <!-- <router-link to='/test' tag='button' class='btn btn-success hw_button'>Test
          </router-link> -->
          <button type="button" class="btn btn-danger hw_button" @click="confirmDels()">批量删除</button>
        </div>
      </div>
      <hr class="middle-hr"></hr>
      <table  class="table table-hover">
        <thead>
          <tr>
            <th class="width-id"><input type="checkbox" v-model="checked" v-on:click="checkAll"/></th>
            <th class="width-th">序号</th>
            <th class="width-th">姓名</th>
            <th class="width-th">性别</th>
            <th class="width-th">出生日期</th>
            <th class="width-th">籍贯</th>
            <th class="width-th">民族</th>
            <th class="width-th">职务</th>
            <th class="width-th">操作</th>
          </tr>
        </thead>
        <!-- <tbody id="tbody"> -->
          <transition-group tag="tbody"
          v-bind:css="false"
          v-on:before-enter="beforeEnter"
          v-on:enter="enter">
          <tr v-for="user,index in content" v-bind:key="user">
            <td><input type="checkbox" name="userid" v-bind:value="choiceId(user.id,user.oid)" v-model="checkedNames" v-on:click="isCheck"/></td>
            <td>{{number*size+index+1}}</td>
            <td>{{user.userName}}</td>
            <td v-if="user.gender == 'male'">男</td>
            <td v-if="user.gender == 'female'">女</td>
            <td>{{user.birthDate}}</td>
            <td>{{user.nativePlace}}</td>
            <td>{{user.nationality}}</td>
            <td>{{user.position}}</td>
            <td>
              <router-link :to="{path:`/user/${choiceId(user.id,user.oid)}`}" class="icon-color" >
                <span  class="glyphicon glyphicon-pencil pencil-color"></span>
              </router-link>
              <a class="icon-color" @click="comfirmDel(choiceId(user.id,user.oid))">
                  <span class="glyphicon glyphicon-trash trash-color"></span>
              </a>
            </td>
          </tr>
          </transition-group>
        <!-- </tbody> -->
      </table>
      <hr class="bottom-hr"></hr>
      <div v-if="totalElements>0">
        <div class="pagination">
          <p v-if="number*size+1 != number*size+numberOfElements">显示第{{number*size+1}}到{{number*size+numberOfElements}}条.共{{totalElements}}条数据</p>
          <p v-else>显示第{{number*size+1}}条数据.共{{totalElements}}条数据.</p>
          <p>共有{{totalPages}}页.转到<input @blur="pageLimit(jumpPageNumber)" class="jump-text" type="number" v-model="jumpPageNumber" @keyup.enter="jumpToPage(jumpPageNumber)"><a @click="jumpToPage(jumpPageNumber)"><span>GO</span></a></p>
        </div>
        <div class="pull-right pull-right-div">
          <ul class="pagination">
            <li v-bind:class="{'disabled': isFrontDisabled }"><a @click="axiosGetUsers(number-1)">前一页</a></li>
            <!-- <li v-if="indexs[0] > 1"><a @click='changePageFront(indexs[5]-5)'>...</a></li> -->
            <li v-for="n in indexs" v-bind:class="{ ' active ': number == n-1}" v-if="n>=1"><a @click="axiosGetUsers(n-1)">{{n}}</a></li>
            <!-- <li v-if="indexs[5] < totalPages"><a @click='changePageBack(indexs[0]+5)'>...</a></li> -->
            <li :class="{'disabled': isEndDisabled }"><a @click="axiosGetUsers(number+1)">后一页</a></li>
          </ul>
        </div>
      </div>
      <div v-if="totalElements<=0">
        <center>
          未找到符合要求的数据。
        </center>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import datepicker from 'vue-date';
import titlePage from '../components/Title';
import constants from '../constants/constans';

const Velocity = require('velocity-animate');

export default {
  name: 'index',
  data() {
    return {
      genders: constants.genders,
      now: `${new Date().getFullYear()}-${new Date().getMonth() + 1}-${new Date().getDate()}`,
      modalDel: false,
      modalDels: false,
      modal_loading: false,
      jumpPageNumber: 1,
      delId: [],
      checked: false,
      isFrontDisabled: false,
      isEndDisabled: false,
      checkedNames: [],
      content: [],
      size: 0,
      number: 0,
      numberOfElements: 0,
      totalElements: 0,
      totalPages: 0,
      userQuery: {
        userName: '',
        gender: 'unlimited',
        position: '',
        startDate: '',
        endDate: '',
      },
      userQuerySend: {
        userName: '',
        gender: 'unlimited',
        position: '',
        startDate: '',
        endDate: '',
      },
    };
  },
  components: {
    datepicker,
    titlePage,
  },
  computed: {
    indexs() {
      let left = 1;
      let right = this.totalPages;
      const arr = [];
      if (this.totalPages >= 6) {
        if (this.number >= 3 && this.number < this.totalPages - 2) {
          left = this.number - 2;
          right = this.number + 3;
        } else if (this.number < 3) {
          left = 1;
          right = 6;
        } else {
          left = this.totalPages - 5;
          right = this.totalPages;
        }
      }
      while (left <= right) {
        arr.push(left);
        left += 1;
      }
      return arr;
    },
  },
  methods: {
    beforeEnter(e) {
      e.style.opacity = 0;
      e.style.height = 0;
    },
    enter(el, done) {
      const delay = el.dataset.index * 150;
      setTimeout(() => {
        Velocity(
          el,
          { opacity: 1, height: '1.6em' },
          { complete: done },
        );
      }, delay);
    },
    valueTrim(value) {
      return value.trim();
    },
    choiceId(pid, oid) {
      let id = '';
      if (oid != null) {
        id = this.transObjectId(oid);
      } else {
        id = pid;
      }
      return id;
    },
    transObjectId(oid) {
      return `${this.validateObjectId(oid.timestamp.toString(16), 4)}` +
      `${this.validateObjectId(oid.machineIdentifier.toString(16), 3)}` +
      `${this.validateObjectId(oid.processIdentifier.toString(16), 2)}` +
      `${this.validateObjectId(oid.counter.toString(16), 3)}`;
    },
    validateObjectId(value, byteSize) {
      let temp = value;
      while (temp.length !== byteSize * 2) {
        temp = `0${temp}`;
      }
      return temp;
    },
    pageLimit(page) {
      if (page <= 0) {
        this.jumpPageNumber = 1;
      } else if (page > this.totalPages) {
        this.jumpPageNumber = this.totalPages;
      }
    },
    jumpToPage(page) {
      this.pageLimit(page);
      this.axiosGetUsers(this.jumpPageNumber - 1);
    },
    isCheck() {
      if (this.checkedNames.length === this.content.length) {
        this.checked = true;
      } else {
        this.checked = false;
      }
    },
    checkAll() {
      if (this.checked === false) {
        this.checkedNames = [];
      } else {
        this.checkedNames = [];
        this.content.forEach((user) => {
          this.checkedNames.push(this.choiceId(user.id, user.oid));
        });
      }
    },
    changePageBack(page) {
      return page >= this.totalPages ?
      this.getUsersList(this.totalPages - 1) : this.getUsersList(page);
    },
    changePageFront(page) {
      return page <= 0 ? this.getUsersList(0) : this.getUsersList(page);
    },
    pageParam(page) {
      if (page <= 0) {
        this.isFrontDisabled = true;
      } else {
        this.isFrontDisabled = false;
      }
      if (page > this.totalPages - 2) {
        this.isEndDisabled = true;
      } else {
        this.isEndDisabled = false;
      }
    },
    confirmDels() {
      if (this.checkedNames.length === 0) {
        this.$Modal.info({
          content: '请至少选中一条信息',
          scrollable: true,
        });
      } else {
        this.modalDels = true;
      }
    },
    comfirmDel(id) {
      this.delId = [id];
      this.modalDel = true;
    },
    delUsers() {
      let ids = [];
      if (this.modalDel === true) {
        ids = this.delId;
      } else {
        ids = this.checkedNames;
      }
      this.modal_loading = true;
      this.$axios({
        method: 'delete',
        url: '/users/delete',
        params: {
          ids: ids.toString(),
        },
        requestHeader: {
          'Content-Type': 'application/json',
        },
      }).then(() => {
        this.modal_loading = false;
        this.modalDels = false;
        this.modalDel = false;
        this.checked = false;
        this.$Message.success({
          content: '删除成功',
          top: 50,
          duration: 3,
        });
        if (this.numberOfElements - ids.length <= 0 && this.number >= 1) {
          this.axiosGetUsers(this.number - 1);
        } else {
          this.axiosGetUsers(this.number);
        }
      }).catch((error) => {
        this.modal_loading = false;
        this.modalDels = false;
        this.modalDel = false;
        this.checked = false;
        this.$Modal.error({
          content: error.message,
        });
      });
    },
    sendUserData() {
      this.userQuerySend.userName = this.userQuery.userName;
      this.userQuerySend.gender = this.userQuery.gender;
      this.userQuerySend.position = this.userQuery.position;
      this.userQuerySend.startDate = this.userQuery.startDate;
      this.userQuerySend.endDate = this.userQuery.endDate;
    },
    getUsersList(page) {
      this.sendUserData();
      this.axiosGetUsers(page);
    },
    axiosGetUsers(page) {
      if (page < 0 || page + 1 > this.totalPages) {
        if (this.totalPages !== 0) {
          return;
        }
      }
      this.checked = false;
      this.checkedNames = [];
      this.$axios({
        url: 'users/find',
        params: {
          page,
          size: 1,
          userName: this.valueTrim(this.userQuerySend.userName),
          gender: this.userQuerySend.gender,
          position: this.valueTrim(this.userQuerySend.position),
          startDate: this.userQuerySend.startDate,
          endDate: this.userQuerySend.endDate,
        },
        method: 'get',
        requestHeader: {
          'Content-Type': 'application/json',
        },
      }).then((response) => {
        this.content = response.data.content;
        this.size = response.data.size;
        this.number = response.data.number;
        this.numberOfElements = response.data.numberOfElements;
        this.totalElements = response.data.totalElements;
        this.totalPages = response.data.totalPages;
        this.pageParam(page);
      }).catch((error) => {
        this.$Modal.error({
          content: error.message,
        });
      });
    },
  },
  mounted() {
    this.axiosGetUsers(0);
  },
};
</script>
<!-- Add 'scoped' attribute to limit CSS to this component only -->
<style scoped lang='scss'>
@import "../assets/scss/index.scss";
</style>
