import './App.css'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import Landing from './components/layout/Landing'
import AuthUser from './views/AuthUser'
import AuthEmployer from './views/AuthEmployer'
import AdminLanding from './components/layout/AdminLanding'
import AuthContextProvider from './contexts/AuthContext'
import PostContextProvider from './contexts/PostContext'
import DashBoard from './views/DashBoard'
import ProtectedRoute from './components/routing/ProtectedRoute'
import Profile from './components/posts/users/UserProfile'
import EmpProfile from './components/posts/employer/EmpProfile'
import EmpPost from './components/posts/employer/EmpPost'
import EmployerPostContextProvider from './contexts/EmployerPostContext'
import AuthAdmin from './views/AuthAdmin'
import AdminPost from './components/posts/admin/AdminPost'
import PostDetail from './components/posts/PostDetail'
import { UserResume } from './components/posts/users/UserResume'
import AdminProfile from './components/posts/admin/AdminProfile'
import EmpPostDetail from './components/posts/employer/EmpPostDetail'
import AdminPostDetail from './components/posts/admin/AdminPostDetail'
import AdminRoute from './components/routing/AdminRoute'
import EmployerRoute from './components/routing/EmployerRoute'
import UserRoute from './components/routing/UserRoute'
import PageNotFound from './components/notfound/PageNotFound'
import EmpOrder from './components/posts/employer/EmpOrders'
import Services from './components/services/Services'
import UnloginRoute from './components/routing/UnloginRoute'
function App() {
    return (
        <AuthContextProvider>
            <PostContextProvider>
                <EmployerPostContextProvider>
                    <Router>
                        <Switch>
                            <Route exact path="/" component={Landing} />
                            <Route path="/user/login" render={(props) => <AuthUser {...props} authRoute="user-login" />} />
                            <Route exact path="/user/register" render={(props) => <AuthUser {...props} authRoute="user-register" />} />
                            <Route path="/employer/login" render={(props) => <AuthEmployer {...props} authRoute="employer-login" />} />
                            <Route exact path="/employer/register" render={(props) => <AuthEmployer {...props} authRoute="employer-register" />} />
                            <Route exact path="/admin/login" render={(props) => <AuthAdmin {...props} authRoute="admin-login" />} />
                            <Route exact path="/admin" component={AdminLanding} />
                            {/* Authenticate to use -19/11 public ROUTE */}

                            <UnloginRoute exact path="/dashboard" component={DashBoard} />
                            <UnloginRoute exact path="/postDetail/:id" component={PostDetail} />
                            <UnloginRoute exact path="/service" component={Services} />

                            {/*User can use */}
                            <UserRoute exact path="/profile" component={Profile} />

                            <ProtectedRoute exact path="/user/resume" component={UserResume} />
                            {/*Employer can use */}

                            <ProtectedRoute exact path="/employer/profile/:id" component={EmpProfile} />
                            <EmployerRoute exact path="/employer/posts" component={EmpPost} />
                            <EmployerRoute exact path="/employer/postdetail/:id" component={EmpPostDetail} />
                            <EmployerRoute exact path="/employer/orders" component={EmpOrder} />

                            {/*Admin can use */}

                            <AdminRoute exact path="/admin/posts" component={AdminPost} />
                            <AdminRoute exact path="/admin/profile" component={AdminProfile} />
                            <AdminRoute exact path="/admin/postdetail/:id" component={AdminPostDetail} />
                            <Route path="/*" component={PageNotFound} />
                        </Switch>
                    </Router>
                </EmployerPostContextProvider>
            </PostContextProvider>
        </AuthContextProvider>
    )
}

export default App
