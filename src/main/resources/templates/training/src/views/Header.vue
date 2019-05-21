<template>
<div class='header'>
    <h1><img src="../assets/logo.png" alt="">Augmentum<span>TM</span></h1>
</div>

</template>
<style scoped lang='scss'>
$primaryColor: #6655AA;
.header {
  background-color: $primaryColor;
  height: 80px;
  h1 {
    color: white;
    padding-left: 30px;
    padding-top: 20px;
    margin-bottom: 10px;
    font-size: 36px;
    font-weight: 500;
    line-height: 1.1;
    img {
      height:35px;
      width:35px;
    }
    span {
      position: relative;
      top: -15px;
      font-size: 10px;
    }
  }
}
</style>
