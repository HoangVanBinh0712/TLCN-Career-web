import Card from 'react-bootstrap/Card'
import moneyIcon from '../../../assets/moneyIcon.png'
import locationIcon from '../../../assets/location.png'
import recruitIcon from '../../../assets/recruitIcon.png'
import startDateIcon from '../../../assets/startDateIcon.png'
import endDateIcon from '../../../assets/endDateIcon.png'
import fieldIcon from '../../../assets/fieldIcon.png'

import { Link } from 'react-router-dom'
import Col from 'react-bootstrap/esm/Col'
import Row from 'react-bootstrap/esm/Row'

const EmployerSingleOrder = ({ order }) => {
    return (
        <Card className="white-space: nowrap card-emp-post" border={order.status === 'PAID' ? 'success' : 'warning'}>
            <Card.Body>
                <Card.Title>
                    <Row>
                        <Col className="col-9">Order for: {order.post.title}</Col>
                        <Col className="con-3" style={{ textAlign: 'right' }}>
                            {order.status !== 'PAID' ? (
                                <Link to={`/payment/${order.id}`} target="_blank">
                                    Paid now !
                                </Link>
                            ) : (
                                ''
                            )}
                        </Col>
                    </Row>
                </Card.Title>
                <Card.Text>
                    <label htmlFor="post-description">
                        <strong>Description </strong>
                    </label>
                    <p id="post-description">{order.post.description}</p>
                </Card.Text>
                <Card.Text>
                    <Row className=" mt-2">
                        <Col>
                            <img src={moneyIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                            {order.post.salary} {order.post.salaryType !== 'NONE' ? order.post.salaryType : ''}
                        </Col>
                        <Col>
                            <img src={recruitIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                            {order.post.recruit} People
                        </Col>
                    </Row>

                    <Row className=" mt-2">
                        <Col>
                            <img src={startDateIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                            {order.post.startDate?.split(' ')[0]}
                        </Col>
                        <Col>
                            <img src={endDateIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                            {order.post.expirationDate?.split(' ')[0]}
                        </Col>
                    </Row>

                    <Row className=" mt-2">
                        <Col>
                            <img src={fieldIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                            {order.post.field}
                        </Col>
                        <Col>
                            <img src={locationIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                            {order.post.city}
                        </Col>
                    </Row>
                </Card.Text>
            </Card.Body>
        </Card>
    )
}
export default EmployerSingleOrder
