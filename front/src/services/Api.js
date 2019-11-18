import axios from 'axios'

export default() => {
    return axios.create({
        baseURL: `/craman-api`,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    })
}
