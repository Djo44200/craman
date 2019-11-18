import Api from '@/services/Api'
export default {

  // return All Users
  getAllUsers () {
    let dataUser = [];
    Api().get('/users').then((response)=>{
      for (let index = 0; response.data && index < response.data.length; index++) {
        const user = response.data[index];
        const element = {
          'name': user.name,
          'idUser': user.idUser,
          'firstName': user.firstName,
        }
        dataUser.push(element);
      }
    })
    return dataUser;
  },

  // return a user
  getOneUser(idUser){
    Api().get('/users/'+idUser);

  }

}
