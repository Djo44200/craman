import axios from 'axios'

export default() => {
    return axios.create({
        baseURL: `/api`,
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS, HEAD',
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
    })
}
