<template>
  <div class="404">
    <h1 class="text-center">(404) Page NotFound</h1>
    <img class="img" src="@/assets/img/404.png"/>
    <div class="center">
      <q-btn color="primary"  :to="{name:'userList'}" label="Return"/>
    </div>
  </div>
</template>

<script>
export default {};
</script>
<style scoped>
.img{
  display: block;
  margin-left: auto;
  margin-right: auto
}
.center{
  text-align: center;
}


</style>
