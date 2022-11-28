import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import App from './App'
import reportWebVitals from './reportWebVitals'
import { QueryClient, QueryClientProvider } from 'react-query'
import axios from 'axios'
import { apiUrl, LOCAL_STORAGE_TOKEN_NAME, REFRESH_TOKEN, USER_ROLE } from './contexts/constants'
import setAuthToken from './utlis/SetAuthToken'

const queryClient = new QueryClient()

const root = ReactDOM.createRoot(document.getElementById('root'))

axios.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem[LOCAL_STORAGE_TOKEN_NAME]
        if (token) {
            config.headers['Authorization'] = 'Bearer ' + token
        }
        // config.headers['Content-Type'] = 'application/json';
        return config
    },
    (error) => {
        Promise.reject(error)
    }
)
axios.interceptors.response.use(
    (response) => {
        return response
    },
    async function (error) {
        if (error.response.status === 401) {
            const refreshToken = localStorage.getItem(REFRESH_TOKEN)
            if (refreshToken) {
                try {
                    const resp = await axios.get(`${apiUrl}/refreshtoken`, {
                        headers: {
                            'refresh-token': refreshToken,
                        },
                    })
                    console.log(resp)
                    localStorage.setItem(LOCAL_STORAGE_TOKEN_NAME, resp.data)
                    setAuthToken(resp.data)
                } catch (error) {
                    console.log(error)
                    let url
                    if (localStorage.getItem(USER_ROLE)) url = `/${localStorage.getItem(USER_ROLE)}/login`
                    else url = '/user/login'
                    localStorage.removeItem(USER_ROLE)
                    localStorage.removeItem(LOCAL_STORAGE_TOKEN_NAME)
                    localStorage.removeItem(REFRESH_TOKEN)
                    setAuthToken(null)
                    window.location.replace(url)
                }
            } else {
                let url
                if (localStorage.getItem(USER_ROLE)) url = `/${localStorage.getItem(USER_ROLE)}/login`
                else url = '/user/login'
                localStorage.removeItem(USER_ROLE)
                localStorage.removeItem(LOCAL_STORAGE_TOKEN_NAME)
                localStorage.removeItem(REFRESH_TOKEN)
                setAuthToken(null)
                window.location.replace(url)
            }

            return Promise.reject(error)
        }

        return Promise.reject(error)
    }
)

root.render(
    <React.StrictMode>
        <QueryClientProvider client={queryClient}>
            <App />
        </QueryClientProvider>
    </React.StrictMode>
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
