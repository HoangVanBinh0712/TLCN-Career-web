import {
  POSTS_LOADED_SUCCESS,
  POSTS_LOADED_FAIL,
  POST_ADDED_SUCCESS,
  POST_UPDATED_SUCCESS,
  POST_DELETED_SUCCESS,
  POST_ACCEPTED_SUCCESS,
  POSTS_FIND_SUCCESS,
  BEFORE_GET_PREPARE,
  POSTS_PREDICT_SUCCESS,
  POSTS_PREDICT_FAIL,
} from "../contexts/constants";

export const PostReducer = (state, action) => {
  const { type, payload} = action;
  switch (type) {
    case POSTS_LOADED_SUCCESS:
      return {
        ...state,
        posts: payload.data,
        postLoading: false,
        currentPage: payload.currentPage,
        totalPage: payload.totalPage,
        limit: payload.limit,
      };
    case POSTS_LOADED_FAIL:
      return {
        ...state,
        posts: [],
        postLoading: false,
      };
    case POST_ADDED_SUCCESS:
      return {
        ...state,
        posts: [...state.posts, payload],
      };
    case POST_UPDATED_SUCCESS:
      const newPosts = state.posts.map((post) => {
        if (post.id === payload.id) return payload;
        return post;
      });
      return {
        ...state,
        posts: newPosts,
      };
    case POST_DELETED_SUCCESS:
      return {
        ...state,
        posts: state.posts.filter((post) => post.id !== payload),
      };
    case POST_ACCEPTED_SUCCESS:
      return {
        ...state,
        posts: state.posts.filter((post) => post.id !== payload),
      };
    case POSTS_FIND_SUCCESS:
      return {
        ...state,
        posts: payload,
        postLoading: false,
      };
    case BEFORE_GET_PREPARE:
      return {
        ...state,
        posts: [],
        postLoading: true,
      };
      case POSTS_PREDICT_SUCCESS:
        return {
          ...state,
          posts: payload.data.data,
          jobOption: payload.data.jobOptionResponses,
          currentView: payload.data.currentView,
        };
      case POSTS_PREDICT_FAIL:
        return {
          ...state,
          posts: [],
          jobOption: 'Can not predict',
          currentView: 'Can not predict',
        };
    default:
      return state;
  }
};
