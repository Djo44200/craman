import Api from '@/services/Api'
export default {
  postRecord(addRecord){
    Api().post('/records',addRecord);
  },
  deleteRecord(removeRecord){
    Api().delete('/records',{ data: removeRecord });
  }
}
