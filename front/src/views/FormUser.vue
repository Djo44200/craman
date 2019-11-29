<template>
  <div id="addUser">
    <template>
      <div class=" formulaire q-pa-md" style="max-width: 400px">
        <h5>Add new user</h5>
        <q-form
      @submit="onSubmit"
      @reset="onReset"
      class="q-gutter-md"
    >
        <q-input
          filled
          v-model="name"
          label="Name *"
          hint="Name"
          name="userName"
          lazy-rules
          :rules="[ val => val && val.length > 0 || 'Please type something']"
        />

        <q-input
          filled
          v-model="firstName"
          label="First Name *"
          hint="First Name"
          name="userFirstName"
          lazy-rules
          :rules="[ val => val && val.length > 0 || 'Please type something']"
        />

        <div class="q-gutter-md row ">
          <q-btn class="btn" label="Submit" type="submit" color="primary"/>
          <q-btn class="btn" label="Reset" type="reset" color="primary"/>
          <q-btn class="btn" color="primary" :to="{name:'userList'}" label="Return"/>
        </div>
      </q-form>
    </div>
  </template>

  <router-view/>
</div>
<!-- </div> -->
</template>

<script>
import { mapState } from 'vuex'
import axios from 'axios'
export default {
  data () {
    return {
      name: null,
      firstName : null,
    }
  },
  computed: mapState({
  }),
  beforeCreate() {
  },

  methods: {
    onSubmit () {
      // send a POST request
      axios({
        method: 'post',
        url: '/api/users',
        data: {
          userName: this.name,
          userFirstName: this.firstName,
        },
        headers: {'Accept': 'application/json','Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS, HEAD'},
          }).then(function (reponse) {
            alert('user created') // TODO quasar notify
            console.error(reponse);
          }).catch(function (erreur) {
            alert('Oops an error has occurred Try again ') // TODO quasar notify
            console.error(erreur);
        });
        },

      onReset () {
        this.name = null;
        this.firstName = null;
      }
    },
  }
  </script>

  <style scoped>

  .formulaire{
    text-align:center;
    margin: auto;
  }
  .btn{
    margin: auto;
  }

  </style>
