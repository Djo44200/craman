<template>
  <div class="userList">
    <h5 class="title"> Welcome</h5>
    <!-- table integration if users -->
    <div class="q-pa-md">
      <div class="q-gutter-md row items-start">
      </div>
      <q-table
      :data="data"
      bordered
      separator="cell"
      :columns="columns"
      :pagination.sync="pagination"
      :filter="filter"
      :filter-method="publicFilterUser"
      row-key="name">
      <template v-slot:top-right>
        <q-input outlined borderless autofocus dense debounce="300" v-model="filter" placeholder="Search">
          <template v-slot:append>
            <q-icon name="search" />
          </template>
        </q-input>
      </template>
      <template v-slot:body="props" :props="props">
        <q-tr>
          <q-td key="desc" >
            <router-link :to="'/user/'+props.row.idUser">{{ props.row.firstName | toUpperCase }} {{ props.row.name }}</router-link>
          </q-td>
        </q-tr>
      </template>
    </q-table>
  </div>
</div>
</template>

<script>
import userService from '@/services/UserService'
export default {
  components: {},
  filters:{
    toUpperCase: function (value){
      if (!value) return ''
      value = value.toString()
      return value.toUpperCase();
    }
  },
  data (){
    return{
      users:[],
      filter: '',
      pagination: {
        rowsPerPage: 0 //  0 for all
      },
      columns: [
        {
          name: 'desc',
          label: 'First Name / Name',
          required: true,
          align: 'left',
          field: row => row.name,
          format: val => `${val}`,
          sortable: true
        },
      ],
      data: []
    }
  },
  mounted() {
    //API Service
    this.data = userService.getAllUsers();
  },
  methods:{
    publicFilterUser (rows, terms) {
      const lowerTerms = terms ? terms.toLowerCase() : '';
      return rows.filter((row) => {
        return row.firstName.toLowerCase().includes(lowerTerms) || row.name.toLowerCase().includes(lowerTerms);
      });
    },
  },
  watch: {
    users: function () {
      for (let index = 0; this.users && index < this.users.length; index++) {
        const user = this.users[index];
        const element = {
          'name': user.name,
          'idUser': user.idUser,
          'firstName': user.firstName,
        }
        this.data.push(element);
      }
    }
  },
}
</script>
<style scoped>
th{
  text-align: left;
}
.title{
  text-align: center;
}
</style>
