<template>
  <div class="title">
    <h1>{{bigTitle}}<small>{{smallTitle}}</small></h1>
  </div>
</template>
<script>
export default {
  name: 'title',
  props: ['bigTitle', 'smallTitle'],
};
</script>

<style scoped lang='scss'>
.title
  h1 {
    margin-top: 15px;
    margin-bottom: 15px;
    margin-left: 60px;
    small {
      margin-left: 10px;
      font-size: 65%;
      font-family: inherit;
      line-height: 1;
    }
  }
</style>
