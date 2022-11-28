export const AuthReducer = (state, action) => {
    const { type, payload: { isAuthenticated, user, isUser, isAdmin, isEmployer, profile, CV } } = action

    switch (type) {
        case 'SET_AUTH':
            return {
                ...state,
                authloading: false,
                isAuthenticated,
                user,
                isUser,
                isEmployer,
                isAdmin,
            }
        case 'PROFILE_LOAD_SUCCESS':
            return {
                ...state,
                profile: profile,
                profileLoading: false,
            }
        case 'CV_UPLOAD_SUCCESS':
            return {
                ...state,
                CV: CV
            }
        case 'CV_SUBMIT_SUCCESS':
            return {
                ...state,
                submited: false
            }

        default:
            return state
    }
}
