import { useState, useContext } from 'react'
import Form from 'react-bootstrap/Form'
import { AuthContext } from '../../../contexts/AuthContext'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'

const AdminProfile = () => {
    let body

    const {
        authState: {
            user: { city, avatar, email, name, phone, address, gender, birth },
        },
    } = useContext(AuthContext)

    body = (
        <div className="ContactInfoView_viewSectionWrapper__SEvGW">
            <Form className="native-grid">
                <Row className="row">
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">Email:</span>
                            <div className="col-8 text-truncate fw-6">{email}</div>
                        </div>
                    </Col>
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">Phone:</span>
                            <div className="col-8 text-truncate fw-6">{phone}</div>
                        </div>
                    </Col>
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">Gender:</span>
                            <div className="col-8 text-truncate fw-6">{gender}</div>
                        </div>
                    </Col>
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">Birth:</span>
                            <div className="col-8 text-truncate fw-6">{birth}</div>
                        </div>
                    </Col>
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">Name:</span>
                            <div className="col-8 text-truncate fw-6">{name}</div>
                        </div>
                    </Col>
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">Addess:</span>
                            <div className="col-8 text-truncate fw-6">{address}</div>
                        </div>
                    </Col>
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">City:</span>
                            <div className="col-8 text-truncate fw-6">{city?.name}</div>
                        </div>
                    </Col>
                </Row>
            </Form>
        </div>
    )

    return (
        <div className="MasterLayout_vContainer__0VU77 MasterLayout_vBackground__YBFVn">
            <div className="MasterLayout_vRow__eF7VB">
                <div className="MasterLayout_vCol__Raypp MasterLayout_vColLg3__rsAKf" style={{ display: 'block' }}>
                    <div className="vnwBox">
                        <p className="title borderBottom"> Career Managerment</p>
                        <ul className="sidebarMenu">
                            {/* <li className='sidebarMenuItem'>
                <Link className = 'profile-link' to='/profile'>
                  <img src={peopleIcon} alt='img' width='30' height='30' className='mr-2'/>
                  <span className='textLabel'> My profile</span>
                </Link>
              </li>
              <li className='sidebarMenuItem'>
                <Link className = 'profile-link' to='/'>
                  <img src={userSeting} alt='img' width='30' height='30' className='mr-2'/>
                  <span className='textLabel'> Profile seting</span>
                </Link>
              </li>
              <li className='sidebarMenuItem'>
                <Link className = 'profile-link' to='/'>
                  <img src={userResumeIcon} alt='img' width='30' height='30' className='mr-2'/>
                  <span className='textLabel'>Account seting</span>
                </Link>
              </li>
              <li className='sidebarMenuItem'>
                <Link className = 'profile-link' to='/'>
                  <img src={passIcon} alt='img' width='30' height='30' className='mr-2'/>
                  <span className='textLabel'>Change Password</span>
                </Link>
              </li> */}
                        </ul>
                    </div>
                </div>

                <div className="MasterLayout_vCol__Raypp MasterLayout_vColLg6__Repj5">
                    <div className="vnwBox vnwBoxSmall">
                        <span className="headerTitle">Admin Profile</span>
                    </div>

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

                            <h2 className="ContactInformation_blockTitle__yHeZl">Thông Tin Cá Nhân</h2>
                            {body}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AdminProfile
