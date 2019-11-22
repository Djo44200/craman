<template>
  <div id="addProject">
    <template>
      <div class=" formulaire q-pa-md" style="max-width: 400px">
        <h5>Add new project</h5>
        <q-form
          @submit="onSubmit"
          @reset="onReset"
          class="q-gutter-md"
        >
        <q-select
          outlined
          v-model="selectProject"
          :options="optionsProject"
          label="Project"
          name="Project1"
          :rules="[ val => val && val != null || 'Please type something']"
        />
        <q-input
          filled
          v-if="selectProject == 'CREATE NEW PROJECT'"
          v-model="nameNewProject"
          label="New project *"
          hint="Name of project"
          name="project"
          lazy-rules
          :rules="[ val => val && val.length > 0 || 'Please type something']"
        />
        <q-select
          outlined
          v-model="selectTeam"
          :options="optionsTeam"
          label="Teams"
          name="team"
          :rules="[ val => val && val != null || 'Please type something']"
        />
        <div class="q-gutter-md row ">
          <q-btn class="btn" label="Submit" type="submit" color="primary"/>
          <q-btn class="btn" label="Reset" type="reset" color="primary" />
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
      selectProject: 'CREATE NEW PROJECT',
      selectTeam: null,
      nameNewProject: null,
      optionsTeam: [],
      optionsProject: [
        "CREATE NEW PROJECT",
      ],
    }
  },
  computed: mapState({
    teams: 'teams',
    projects: 'projects'
  }),
  beforeCreate() {
    this.$store.dispatch('searchAllTeam');
    this.$store.dispatch('searchAllProject');
  },
  watch: {
    // val is the new value of the state when teamList change !
    teams: function (val) {
      for (let index = 0; val && index < val.length; index++) {
        const team = val[index];
        this.optionsTeam.push(team.name);
      }
    },
    projects: function (val) {
      for (let index = 0; val && index < val.length; index++) {
        const project = val[index];
        this.optionsProject.push(project.name);
      }
    },
  },
  methods: {
    onSubmit () {
      // send a POST request
      axios({
        method: 'post',
        url: '/craman-api/projects',
        data: {
          Project: this.selectProject,
          NewProject: this.nameNewProject,
          Team: this.selectTeam,
        },
        headers: {'Accept': 'application/json'},
          }).then(function (reponse) {
            alert('Project created')

          }).catch(function (erreur) {
            alert('Oops an error has occurred Try again ')
            console.log(erreur);
        });
        
        },
      onReset () {
        console.log('coucou');
        this.name = null
        this.selectProject = null
        this.selectTeam = null
      }
    },
  }
  </script>

  <style scoped>
  .formulaire {
    text-align:center;
    margin: auto;
  }
  .btn {
    margin: auto;
  }
  </style>
