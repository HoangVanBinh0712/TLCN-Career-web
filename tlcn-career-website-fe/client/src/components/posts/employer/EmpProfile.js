import { useContext, useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import peopleIcon from '../../../assets/profile-icon.png'
import passIcon from '../../../assets/password-icon.png'
import '../../css/EmployerProfile.css'
import { useParams } from 'react-router-dom'
import Spinner from 'react-bootstrap/esm/Spinner'
import axios from 'axios'
import { apiUrl } from '../../../contexts/constants'
import { useQuery } from 'react-query'
import { AuthContext } from '../../../contexts/AuthContext'
import EmpUpdateProfileModal from './modals/EmpUpdateProfileModal'
import ChangePasswordModal from './modals/ChangePasswordModal'

const EmpProfile = () => {
    const { id } = useParams()

    const {
        authState: { user, isEmployer },
    } = useContext(AuthContext)

    //Create State
    const [showUpdate, setShowUpdate] = useState(false)

    const [showChangePassword, setShowChangePassword] = useState(false)

    //Fetch data
    const getEmpInfo = async (id) => {
        try {
            const response = await axios.get(`${apiUrl}/employer/infomation/${id}`)
            return response.data
        } catch (err) {
            console.log(err)
            return null
        }
    }

    const { data, status } = useQuery(['empInfo'], async () => await getEmpInfo(id))

    let body
    let modals
    if (status === 'success') {
        //Create variable from state to show on html
        if (isEmployer && user.id === id)
            modals = (
                <>
                    <div>{showUpdate ? <EmpUpdateProfileModal user={user} showUpdate={showUpdate} setShowUpdate={setShowUpdate} /> : ''}</div>
                    <div>
                        <ChangePasswordModal showChangePassword={showChangePassword} setShowChangePassword={setShowChangePassword} />
                    </div>
                </>
            )
        body = (
            <div className="ContactInfoView_viewSectionWrapper__SEvGW">
                <figure className="snip1336">
                    <img
                        src={
                            'https://media.istockphoto.com/photos/business-meeting-picture-id637416508?k=6&m=637416508&s=170667a&w=0&h=cz8gcvu814y4jalwk6rCd6C5g0uNfMUlQAJdsCeQrCg='
                        }
                        alt="sample87"
                    />
                    <figcaption>
                        {data.avatar ? <img src={data.avatar} alt="profile-sample4" className="profile" /> : ' '}
                        <h2>
                            {data.name}
                            <span>Company Size: {data.employee}</span>
                        </h2>
                        <h3>Description </h3>
                        <div className="emp-description">
                            {data.description?.split('\n').map((line) => (
                                <p>{line}</p>
                            ))}
                        </div>
                        <h3>Field of Work</h3>
                        <p>{data.field.name}</p>
                        <h3>Location</h3>
                        <p>{data.city.name + ', ' + data.address}</p>
                        <h3>Contact Infomation</h3>
                        <p>
                            Email : {data.email} <br></br>
                            Phone: {data.phone}
                        </p>
                        <a href="/" className="follow">
                            Follow
                        </a>
                    </figcaption>
                </figure>
            </div>
        )
    } else
        body = (
            <div className="d-flex justify-content-center mt-2">
                <Spinner animation="border" variant="info" />
            </div>
        )

    return (
        <div className="MasterLayout_vContainer__0VU77 MasterLayout_vBackground__YBFVn">
            <div className="MasterLayout_vRow__eF7VB">
                <div className="MasterLayout_vCol__Raypp MasterLayout_vColLg3__rsAKf" style={{ display: 'block' }}>
                    {isEmployer && user.id === id ? (
                        <div className="vnwBox">
                            <p className="title borderBottom"> Career Managerment</p>
                            <ul className="sidebarMenu">
                                <li className="sidebarMenuItem">
                                    <Link
                                        className="profile-link"
                                        to="#"
                                        onClick={() => {
                                            setShowUpdate(true)
                                        }}
                                    >
                                        <img src={peopleIcon} alt="img" width="30" height="30" className="mr-2" />
                                        <span className="textLabel"> Update profile</span>
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
                                <li className="sidebarMenuItem">
                                    <img src={passIcon} alt="img" width="30" height="30" className="mr-2" />
                                    <a className="textLabel" href="/employer/orders">
                                        Your orders
                                    </a>
                                </li>
                            </ul>
                        </div>
                    ) : (
                        ''
                    )}
                </div>

                <div className="MasterLayout_vCol__Raypp MasterLayout_vColLg6__Repj5">
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
                            {modals}
                            {body}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EmpProfile
