import { Link } from 'react-router-dom'
import peopleIcon from '../../../assets/profile-icon.png'
import userSeting from '../../../assets/upload-icon.png'
import userResumeIcon from '../../../assets/resume-icon.png'
import passIcon from '../../../assets/password-icon.png'
import { useState, useContext } from 'react'
import { AuthContext } from '../../../contexts/AuthContext'
import { apiUrl } from '../../../contexts/constants'
import axios from 'axios'
import { useQuery } from 'react-query'
import Spinner from 'react-bootstrap/esm/Spinner'
import UserInformation from './UserInformation'
import '../../css/UserPorfile.css'
import UserUpdateInformation from './UserUpdateInformation'
import ChangePasswordModal from './ChangePasswordModal'
import UserUploadCV from './UserUploadCV'

const Profile = () => {
    let body

    const [update, setUpdate] = useState(false)
    const [upload, setUpload] = useState(false)
    const [updateLoading, setUpdateLoading] = useState(false)

    const {
        authState: { user },
    } = useContext(AuthContext)

    //Fetch data (field and city)
    const getCity = async () => {
        try {
            const responseCity = await axios.get(`${apiUrl}/city`)

            return responseCity.data.data
        } catch (err) {
            console.log(err)
            return null
        }
    }

    const { data, status } = useQuery(['city'], async () => await getCity())

    //End fetch data
    const [showChangePassword, setShowChangePassword] = useState(false)

    let cvName = 'There are no profiles yet'
    if (user.profiles.length !== 0) {
        cvName = user.profiles[0].name
    }

    if (update) {
        if (status === 'success') {
            body = (
                <>
                    <h2 className="ContactInformation_blockTitle__yHeZl">Infomation</h2>
                    <div className="ContactInfoView_viewSectionWrapper__SEvGW">
                        <UserUpdateInformation user={user} setUpdateLoading={setUpdateLoading} setUpdate={setUpdate} updateLoading={updateLoading} data={data} />
                    </div>
                </>
            )
        } else {
            body = (
                <div className="d-flex justify-content-center mt-2">
                    <Spinner animation="border" variant="info" />
                </div>
            )
        }
    } else if (upload) {
        body = <UserUploadCV setUpdate={setUpdate} />
    } else {
        body = (
            <>
                <h2 className="ContactInformation_blockTitle__yHeZl">Infomation</h2>
                <div className="ContactInfoView_viewSectionWrapper__SEvGW">
                    <UserInformation user={user} cvName={cvName} setUpdate={setUpdate} />
                </div>
            </>
        )
    }

    return (
        <div className="MasterLayout_vContainer__0VU77 MasterLayout_vBackground__YBFVn">
            <div></div>
            <div className="MasterLayout_vRow__eF7VB">
                <div className="MasterLayout_vCol__Raypp MasterLayout_vColLg3__rsAKf" style={{ display: 'block' }}>
                    <div className="vnwBox">
                        <p className="title borderBottom"> Career Managerment</p>
                        <ul className="sidebarMenu">
                            <li className="sidebarMenuItem">
                                <Link className="profile-link" to="/profile">
                                    <img src={peopleIcon} alt="img" width="30" height="30" className="mr-2" />
                                    <span
                                        className="textLabel"
                                        onClick={() => {
                                            setUpload(false)
                                            setUpdate(false)
                                        }}
                                    >
                                        My profile
                                    </span>
                                </Link>
                            </li>
                            <li className="sidebarMenuItem">
                                <Link className="profile-link" to="/profile">
                                    <img src={userSeting} alt="img" width="30" height="30" className="mr-2" />
                                    <span
                                        className="textLabel"
                                        onClick={() => {
                                            setUpload(true)
                                            setUpdate(false)
                                        }}
                                    >
                                        Upload CV
                                    </span>
                                </Link>
                            </li>
                            <li className="sidebarMenuItem">
                                <Link className="profile-link" to="/user/resume">
                                    <img src={userResumeIcon} alt="img" width="30" height="30" className="mr-2" />
                                    <span
                                        className="textLabel"
                                        onClick={() => {
                                            setUpload(false)
                                            setUpdate(false)
                                        }}
                                    >
                                        CV Managerment
                                    </span>
                                </Link>
                            </li>
                            <li className="sidebarMenuItem">
                                <Link
                                    className="profile-link"
                                    to="#"
                                    onClick={() => {
                                        setShowChangePassword(true)
                                    }}
                                >
                                    <img src={passIcon} alt="img" width="30" height="30" className="mr-2" />
                                    <span className="textLabel">Change Password</span>
                                </Link>
                            </li>
                        </ul>
                    </div>
                </div>

                <div className="MasterLayout_vCol__Raypp MasterLayout_vColLg6__Repj5">
                    <div className="vnwBox vnwBoxSmall">
                        <span className="headerTitle">My Profile</span>
                    </div>
                    <ChangePasswordModal setShowChangePassword={setShowChangePassword} showChangePassword={showChangePassword} />
                    <div className="Block_Block___z99z ">
                        <div className="ContactInformation_contactInformationComponent__XmtOM">
                            <svg
                                className="ContactInformation_editIcon__nzify ContactInformation_showEditIcon__NCFxa"
                                width={'25'}
                                height={'25'}
                                viewBox={'0 0 32 32'}
                                xmlns={'http://www.w3.org/2000/svg'}
                                xmlnsXlink={'http://www.w3.org/1999/xlink'}
                            ></svg>

                            {body}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Profile
