//import _ from 'lodash';
import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
const URL = '/craman-api'
export default new Vuex.Store({
  state: {
    users: [],
    user: {},
    teams:[],
    projects:[],
    records:[],
    quarters:[],
    listQuarters:[],
  },

  mutations: {
    userList(state, users) {
      state.users = users;
    },
    user(state, user) {
      state.user = user;
    },
    teamList(state, teams) {
      state.teams = teams;
    },
    projectList(state, projects) {
      state.projects = projects;
    },
    recordsList(state, records){
      state.records = records;
    },
    addRecord(state, record) {
      state.records = [...state.records, record];
    },
    removeRecord(state, record) {
      state.records.splice(state.records.indexOf(record));
    },
    quartersList(state, listQuarters){
      state.listQuarters = listQuarters;
    },
  },
  actions: {
    searchOneUser({ commit }, id) {
      Vue.axios({ // TODO put in requestService
        'method': 'GET',
        'url': URL+'/users/' + id,
      }).then((response) => {
        commit('user', response.data)
      });
    },
    searchAllTeam({ commit }) {
      Vue.axios({ // TODO put in requestService
        'method': 'GET',
        'url': URL+'/teams/'
      }).then((response) => {
        commit('teamList', response.data)
      });

    },

    searchAllTeamHisProject({ commit }) {
      Vue.axios({ // TODO put in requestService
        'method': 'GET',
        'url': URL+'/teams/teamsProject'
      }).then((response) => {
        commit('teamList', response.data)
      });

    },
    searchAllProject({ commit }) {
      Vue.axios({ // TODO put in requestService
        'method': 'GET',
        'url': URL+'/projects'
      }).then((response) => {
        commit('projectList', response.data)
      });
    },
    searchRecordByDate({ commit }, params) {
      const { startDate, endDate, id } = params;
      Vue.axios({  // TODO put in requestService
        'method': 'GET',
        'url': URL+'/records/'+startDate+'/'+endDate+'/'+id,
      }).then((response) => {
        commit('recordsList', response.data)
      });
    },
    searchQuartersByDate({ dispatch, commit }, params) {
      const { startDate, endDate, id } = params;
      Vue.axios({ // TODO put in requestService
        'method': 'GET',
        'url': URL+'/records/quarters/'+startDate+'/'+endDate+'/'+id,
      }).then((response) => {
        commit('quartersList', response.data);
        dispatch('searchRecordByDate', params);
      });
    },
    searchQuartersTrimester({ commit }, params) {
      const { startDate, endDate } = params;
      Vue.axios({ // TODO put in requestService
        'method': 'GET',
        'url': URL+'/records/trimester/'+startDate+'/'+endDate,
      }).then((response) => {
        commit('recordsList', response.data)
      });
    },
    async getAddRecord({commit},addRecord) {
      Vue.axios({ // TODO put in requestService
        'method': 'post',
        'data': addRecord,
        'url': URL+'/records',
      }).then(() => {
        commit('addRecord', addRecord)
      });
    },
    async getRemoveRecord({commit},removeRecord) {
      Vue.axios({ // TODO put in requestService
        'method': 'delete',
        'data': removeRecord,
        'url': URL+'/records',
      }).then(() => {
        commit('removeRecord', removeRecord)
      });
    },

  },
})
