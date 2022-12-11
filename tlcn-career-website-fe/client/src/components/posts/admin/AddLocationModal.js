import Modal from 'react-bootstrap/Modal'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import { useState } from 'react'
import axios from 'axios'
import { apiUrl } from '../../../contexts/constants'
import swal from 'sweetalert'
const AddLocationModal = ({ showLocationModal, setShowLocationModal }) => {
    // State
    const [newLocation, setNewLocation] = useState({
        code: '',
        name: '',
    })
    const [updateLoading, setUpdateLoading] = useState(false)

    const { code, name } = newLocation

    const onChangeLocationForm = (event) => setNewLocation({ ...newLocation, [event.target.name]: event.target.value })

    const closeDialog = () => {
        setNewLocation({
            code: '',
            name: '',
        })
        setShowLocationModal(false)
    }
    const onSubmit = async (event) => {
        event.preventDefault()
        setUpdateLoading(true)
        try {
            const response = await axios.post(`${apiUrl}/admin/city`, newLocation)
            if (response.data.success) {
                swal('Success', 'Add new Location success', 'success')
            }
        } catch (error) {
            swal('Error', 'Error when adding Location !', 'error')
        }
        setUpdateLoading(false)
        closeDialog()
    }
    let body
    body = (
        <Modal className="modal-add-Location" show={showLocationModal} onHide={closeDialog}>
            <Modal.Header closeButton>
                <Modal.Title>Add New Location</Modal.Title>
            </Modal.Header>
            <Form onSubmit={onSubmit}>
                <Modal.Body>
                    <Form.Group className="mb-3">
                        <Form.Text id="title-help" muted>
                            Code
                        </Form.Text>
                        <Form.Control type="text" placeholder="Code" name="code" required aria-describedby="title-help" value={code} onChange={onChangeLocationForm} />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Text id="title-help" muted>
                            Name
                        </Form.Text>
                        <Form.Control type="text" placeholder="Name" name="name" required aria-describedby="title-help" value={name} onChange={onChangeLocationForm} />
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
export default AddLocationModal
