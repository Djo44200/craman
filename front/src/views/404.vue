<template>
  <div class="404">

    <img class=" text-center img img-fluid" src="@/assets/img/404.png"/>
    <br>
    <div class="text-center">
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
  margin-right: auto;
  max-width: 100%;
  height: auto;
}
</style>
