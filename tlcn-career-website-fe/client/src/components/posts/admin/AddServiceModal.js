import Modal from 'react-bootstrap/Modal'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import { useState } from 'react'
import axios from 'axios'
import { apiUrl } from '../../../contexts/constants'
import swal from 'sweetalert'
const AddServiceModal = ({ showServiceModal, setShowServiceModal, services, setServices }) => {
    // State
    const [newService, setNewService] = useState({
        name: '',
        description: '',
        cvSubmitAmount: 0,
        price: 0,
        currency: '',
    })
    const [updateLoading, setUpdateLoading] = useState(false)

    const { name, description, cvSubmitAmount, price, currency } = newService

    const onChangeNewPostForm = (event) => setNewService({ ...newService, [event.target.name]: event.target.value })

    const closeDialog = () => {
        setNewService({
            name: '',
            description: '',
            cvSubmitAmount: 0,
            price: 0,
            currency: '',
        })
        setShowServiceModal(false)
    }
    const onSubmit = async (event) => {
        event.preventDefault()
        setUpdateLoading(true)
        try {
            const response = await axios.post(`${apiUrl}/admin/service`, newService)
            if (response.data.success) {
                swal('Success', 'Add new Service success', 'success')
                services.push(response.data.data)
                //  setServices(services)
            }
        } catch (error) {
            swal('Error', 'Error when adding service !', 'error')
        }
        setUpdateLoading(false)
    }
    let body
    body = (
        <Modal className="modal-add-service" show={showServiceModal} onHide={closeDialog}>
            <Modal.Header closeButton>
                <Modal.Title>Add New Service</Modal.Title>
            </Modal.Header>
            <Form onSubmit={onSubmit}>
                <Modal.Body>
                    <Form.Group className="mb-3">
                        <Form.Text id="title-help" muted>
                            Name
                        </Form.Text>
                        <Form.Control type="text" placeholder="Name" name="name" required aria-describedby="title-help" value={name} onChange={onChangeNewPostForm} />
                    </Form.Group>
                    <Form.Text id="title-help" muted>
                        Description
                    </Form.Text>
                    <Form.Group className="mb-3">
                        <Form.Control as="textarea" rows={3} placeholder="Description" name="description" value={description} onChange={onChangeNewPostForm} />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Text id="title-help" muted>
                            CV Submit Amount
                        </Form.Text>
                        <Form.Control type="number" placeholder="CV Submit Amount" name="cvSubmitAmount" required value={cvSubmitAmount} onChange={onChangeNewPostForm} />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Text id="title-help" muted>
                            Price
                        </Form.Text>
                        <Form.Control type="number" placeholder="Price" name="price" required value={price} onChange={onChangeNewPostForm} />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Text id="title-help" muted>
                            Currency
                        </Form.Text>
                        <Form.Control type="text" placeholder="VND or USD" name="currency" required value={currency} onChange={onChangeNewPostForm} />
                    </Form.Group>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={closeDialog}>
                        Cancel
                    </Button>
                    <Button variant="primary" type="submit" disabled={updateLoading}>
                        {updateLoading && <span className="spinner-border spinner-border-sm mr-1"></span>}
                        Confirm
                    </Button>
                </Modal.Footer>
            </Form>
        </Modal>
    )

    return body
}
export default AddServiceModal
