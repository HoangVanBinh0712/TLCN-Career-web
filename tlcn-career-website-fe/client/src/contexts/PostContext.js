import axios from 'axios'
import { createContext, useReducer } from 'react'
import { PostReducer } from '../reducers/PostReducer'
import { apiUrl, POSTS_LOADED_FAIL, POSTS_LOADED_SUCCESS, POST_FIND_SUCCESS } from './constants'

export const PostContext = createContext()

const PostContextProvider = ({ children }) => {
    // state
    const [postState, dispatch] = useReducer(PostReducer, {
        posts: [],
        post: null,
        postLoading: true,
        currentPage: null,
        totalPage: null,
        limit: null,
    })

    // get api post
    const getPosts = async (key) => {
        let str = ''
        if (key !== undefined) str = key
        try {
            const response = await axios.get(`${apiUrl}/post?${str}`)
            if (response.data.success) {
                dispatch({ type: POSTS_LOADED_SUCCESS, payload: response.data })
            } else {
                dispatch({ type: POSTS_LOADED_FAIL })
            }
        } catch (error) {
            dispatch({ type: POSTS_LOADED_FAIL })
        }
    }

    const findPostById = async (postId) => {
        try {
            const response = await axios.get(`${apiUrl}/post/${postId}`)
            if (response.data.success) {
                dispatch({ type: POST_FIND_SUCCESS, payload: response.data.data })
            }
            return response.data.post
        } catch (error) {
            console.log(error)
            dispatch({ type: POSTS_LOADED_FAIL })
            return error
        }
    }

    // postcontextdata
    const postContextData = { postState, getPosts, findPostById }

    return <PostContext.Provider value={postContextData}>{children}</PostContext.Provider>
}

export default PostContextProvider
