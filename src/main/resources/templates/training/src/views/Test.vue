<template>
  <div calss="test">
    <!-- 按钮触发模态框 -->
    <button class="btn btn-primary btn-lg"  data-toggle="modal" data-target="#myModal"></button>
    <comfirmModal ref="comfirmModal" message="i'm shirley" v-on:excute="testExcute"> </comfirmModal>
  </div>
</template>
<script>
import comfirmModal from '../components/comfirmModal';

export default {
  name: 'test',
  data() {
    return {
      message: '',
      aaa: 'adasd',
    };
  },
  components: {
    comfirmModal,
  },
  methods: {
    testExcute(msg) {
      // this.$refs.comfirmModal.mess = 'hdkajdasd';
      alert(msg);
      // console.log(this.$refs.comfirmModal.mess);
    },
  },
};
</script>
