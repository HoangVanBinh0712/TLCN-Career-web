import Card from 'react-bootstrap/Card'
import moneyIcon from '../../assets/moneyIcon.png'
import recruitIcon from '../../assets/recruitIcon.png'
import Col from 'react-bootstrap/esm/Col'
import Row from 'react-bootstrap/esm/Row'

const SingleService = ({ service }) => {
    const type = service?.name.split('-')[0]
    const lib = {
        BASIC: 'success',
        ADVANCE: 'warning',
        PREMIUM: 'danger',
    }
    let body
    if (type === 'PREMIUM') {
        body = (
            <Card className="white-space: nowrap card-service service-border premium-service-border ">
                <Card.Body>
                    <Card.Title>
                        <Row>
                            <Col className="col-12">{service.name}</Col>
                        </Row>
                    </Card.Title>
                    <Card.Text>
                        <label for="post-description">
                            <strong>Description </strong>
                        </label>
                        <p id="post-description">{service.description}</p>
                    </Card.Text>
                    <Card.Text>
                        <Row className=" mt-2">
                            <Col>
                                <img src={moneyIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                                {service.price} {service.currency}
                            </Col>
                            <Col>
                                <img src={recruitIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                                {service.cvSubmitAmount} People
                            </Col>
                        </Row>
                    </Card.Text>
                </Card.Body>
            </Card>
        )
    } else if (type === 'ADVANCE') {
        body = (
            <Card className="white-space: nowrap card-service service-border advance-service-border ">
                <Card.Body>
                    <Card.Title>
                        <Row>
                            <Col className="col-12">{service.name}</Col>
                        </Row>
                    </Card.Title>
                    <Card.Text>
                        <label for="post-description">
                            <strong>Description </strong>
                        </label>
                        <p id="post-description">{service.description}</p>
                    </Card.Text>
                    <Card.Text>
                        <Row className=" mt-2">
                            <Col>
                                <img src={moneyIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                                {service.price} {service.currency}
                            </Col>
                            <Col>
                                <img src={recruitIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                                {service.cvSubmitAmount} People
                            </Col>
                        </Row>
                    </Card.Text>
                </Card.Body>
            </Card>
        )
    } else {
        body = (
            <Card className="white-space: nowrap card-service service-border basic-service-border ">
                <Card.Body>
                    <Card.Title>
                        <Row>
                            <Col className="col-12">{service.name}</Col>
                        </Row>
                    </Card.Title>
                    <Card.Text>
                        <label for="post-description">
                            <strong>Description </strong>
                        </label>
                        <p id="post-description">{service.description}</p>
                    </Card.Text>
                    <Card.Text>
                        <Row className=" mt-2">
                            <Col>
                                <img src={moneyIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                                {service.price} {service.currency}
                            </Col>
                            <Col>
                                <img src={recruitIcon} width="40px" height="30px" style={{ marginRight: '10px' }} alt="img.png" />
                                {service.cvSubmitAmount} People
                            </Col>
                        </Row>
                    </Card.Text>
                </Card.Body>
            </Card>
        )
    }

    return body
}
export default SingleService
