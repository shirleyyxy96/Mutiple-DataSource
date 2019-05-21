<template>
  <div id="comfirmModal">
<!-- 模态框（Modal） -->
<div  class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					模态框（Modal）标题
				</h4>
			</div>
			<div class="modal-body">
				{{mess}}
			</div>
			<div class="modal-footer">
        <button type="button" class="btn btn-default" @click="testFun">测试button
				</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" @click="excute()">
					提交更改
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
</div>
</template>
<script>
  export default {
    name: 'comfirmModal',
    props: ['message'],
    data() {
      return {
        mess: this.message,
      };
    },
    computed: {
      message2() {
        return this.mess;
      },
    },
    methods: {
      testFun() {
        this.mess = new Date();
      },
      excute() {
        this.testFun();
        this.$emit('excute', this.mess);
      },
    },
  };
</script>
