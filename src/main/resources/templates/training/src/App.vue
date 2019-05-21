<template>
  <div id="app">
    <newheader></newheader>
    <router-view></router-view>
  </div>
</template>

<script>
import header from '@/views/Header';

export default {
  name: 'app',
  components: {
    newheader: header,
  },
};
</script>
<style lang='scss'>
#app {
  font-size: 15px;
}
</style>
