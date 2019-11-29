import Vue from 'vue'
import Vuex from 'vuex'
import Api from '@/services/Api'
Vue.use(Vuex)
export default new Vuex.Store({
  state: {
    user: {},
    teams:[],
    projects:[],
    records:[],
    quarters:[],
    listQuarters:[],
  },

  mutations: {
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
      Api().get('/users/'+id).then((response) => {
        commit('user', response.data)
      });
    },
    searchAllTeam({ commit }) {
      Api().get('/teams/').then((response) => {
        commit('teamList', response.data)
      });
    },

    searchAllTeamHisProject({ commit }) {
      Api().get('/teams/teamsProject').then((response) => {
        commit('teamList', response.data)
      });
    },
    searchAllProject({ commit }) {
      Api().get('/projects').then((response) => {
        commit('projectList', response.data)
      });
    },
    searchRecordByDate({ commit }, params) {
      const { startDate, endDate, id } = params;
      Api().get('/records/'+startDate+'/'+endDate+'/'+id).then((response) => {
        commit('recordsList', response.data)
      });
    },
    searchQuartersByDate({ dispatch, commit }, params) {
      const { startDate, endDate, id } = params;
      Api().get('/records/quarters/'+startDate+'/'+endDate+'/'+id).then((response) => {
        commit('quartersList', response.data);
        dispatch('searchRecordByDate', params);
      });
    },
    searchQuartersTrimester({ commit }, params) {
      const { startDate, endDate } = params;
      Api().get('/records/trimester/'+startDate+'/'+endDate).then((response) => {
        commit('recordsList', response.data)
      });
    },
    async getAddRecord({commit},addRecord) {
      Api().post('/records',addRecord ).then(() => {
        commit('addRecord', addRecord)
      }).catch(function (error) {
        console.log(error);
        alert ('Identical value')
      });
    },
    async getRemoveRecord({commit},removeRecord) {
      Api().delete('/records',{data : removeRecord} ).then(() => {
        commit('removeRecord', removeRecord)
      });
    },
  },
})
