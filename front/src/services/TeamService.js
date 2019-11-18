import Api from '@/services/Api'
export default {
    getAllTeamsHisProject () {
      let dataTeam = [];
      Api().get('/teams/teamsProject').then((response)=>{
        for (let index = 0; response.data && index < response.data.length; index++) {
          const team = response.data[index];
          const element = {
            'idProject': team.idProject,
            'idTeam': team.idTeam,
            'name': team.name,
            'nameProject': team.nameProject,
          }
          dataTeam.push(element);
        }
      })
      return dataTeam;
    }

}
