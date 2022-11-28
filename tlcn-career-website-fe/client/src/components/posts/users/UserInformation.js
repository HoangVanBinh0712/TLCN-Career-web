import Button from 'react-bootstrap/esm/Button'
import Col from 'react-bootstrap/esm/Col'
import Row from 'react-bootstrap/esm/Row'
import Form from 'react-bootstrap/Form'
import avatarIcon from '../../../assets/avatarIcon.png'

const UserInformation = ({ user, cvName, setUpdate }) => {
    const NotAddYet = '..................'
    return (
        <>
            <div className="user-avatar-block">
                <img src={user.avatar ? user.avatar : avatarIcon} alt="img" />
            </div>
            <Form className="native-grid">
                <Row className="row">
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">Email:</span>
                            <div className="col-8 text-truncate fw-6" style={{ width: '100%' }}>
                                {user.email}
                            </div>
                        </div>
                    </Col>
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">Phone:</span>
                            <div className="col-8 text-truncate fw-6" style={{ width: '100%' }}>
                                {user.phone}
                            </div>
                        </div>
                    </Col>
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">Name:</span>
                            <div className="col-8 text-truncate fw-6" style={{ width: '100%' }}>
                                {user.name}
                            </div>
                        </div>
                    </Col>
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">Gender:</span>
                            <div className="col-8 text-truncate fw-6" style={{ width: '100%' }}>
                                {user.gender ? user.gender : NotAddYet}
                            </div>
                        </div>
                    </Col>
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">Birth:</span>
                            <div className="col-8 text-truncate fw-6" style={{ width: '100%' }}>
                                {user.birth ? user.birth : NotAddYet}
                            </div>
                        </div>
                    </Col>
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">Addess:</span>
                            <div className="col-8 text-truncate fw-6" style={{ width: '100%' }}>
                                {user.address ? user.address : NotAddYet}
                            </div>
                        </div>
                    </Col>
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">City:</span>
                            <div className="col-8 text-truncate fw-6" style={{ width: '100%' }}>
                                {user.city ? user.city.name : NotAddYet}
                            </div>
                        </div>
                    </Col>
                    <Col className="col-12 col-lg-6">
                        <div className="undefined row">
                            <span className="undefined col-4">Resume:</span>
                            <div className="col-8 text-truncate fw-6" style={{ width: '100%' }}>
                                {cvName} .pdf
                            </div>
                        </div>
                    </Col>
                </Row>
                <Button
                    variant="success"
                    onClick={() => {
                        setUpdate(true)
                    }}
                >
                    Update
                </Button>
            </Form>
        </>
    )
}

export default UserInformation
