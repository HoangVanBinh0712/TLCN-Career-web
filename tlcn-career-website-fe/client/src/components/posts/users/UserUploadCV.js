import Form from 'react-bootstrap/Form'
import { useContext, useState } from 'react'
import AlertMessage from '../../layout/AlertMessage'
import Button from 'react-bootstrap/esm/Button'
import { AuthContext } from '../../../contexts/AuthContext'

const UserUploadCV = ({ setUpdate }) => {
    const { uploadUserCV } = useContext(AuthContext)
    const [userCV, setUserCV] = useState({
        CV: '',
        name: '',
        isDefault: false,
    })

    const { CV, name, isDefault } = userCV
    const [updateLoading, setUpdateLoading] = useState(false)

    const [alert, setAlert] = useState(null)
    const fileToBase64 = (file, cb) => {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = function () {
            cb(null, reader.result)
        }
        reader.onerror = function (error) {
            cb(error, null)
        }
    }
    const onUploadFileChange = ({ target }) => {
        if (target.files < 1 || !target.validity.valid) {
            return
        }

        fileToBase64(target.files[0], (err, result) => {
            if (result) {
                setUserCV({
                    ...userCV,
                    CV: target.files[0],
                })
            }
        })
    }
    const onChangeUserCV = (event) =>
        setUserCV({
            ...userCV,
            [event.target.name]: event.target.value,
        })

    const onSubmitCV = async (event) => {
        setUpdateLoading(true)
        event.preventDefault()
        try {
            const cvUpload = await uploadUserCV(userCV)
            console.log(cvUpload)
            if (cvUpload.success) {
                setAlert({ type: 'success', message: 'Upload successfull' })
                setTimeout(() => setAlert(null), 10000)
            } else {
                setAlert({ type: 'danger', message: cvUpload.message })
                setTimeout(() => setAlert(null), 10000)
            }
        } catch (error) {
            console.log(error)
        }
        setUpdateLoading(false)
    }
    return (
        <>
            <h2 className="ContactInformation_blockTitle__yHeZl">Upload Your Resume</h2>
            <div className="ContactInfoView_viewSectionWrapper__SEvGW">
                <Form className="native-grid" onSubmit={onSubmitCV}>
                    <AlertMessage info={alert} />
                    <Form.Group>
                        <Form.Label> Name</Form.Label>
                        <Form.Control type="text" name="name" value={name} onChange={onChangeUserCV} required/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> Is Default</Form.Label>
                        <Form.Control type="text" placeholder="Ex: true/false" name="isDefault" value={isDefault} onChange={onChangeUserCV} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label> CV</Form.Label>
                        <Form.Control name="CV" type="file" accept="application/pdf" onChange={onUploadFileChange} required/>
                    </Form.Group>

                    <Button
                        className="update-change-info-button mt-3"
                        variant="warning"
                        onClick={() => {
                            setUpdate(false)
                        }}
                    >
                        Cancel
                    </Button>
                    <Button disabled={updateLoading} type="submit" className="update-change-info-button mt-3" variant="success">
                        {updateLoading && <span className="spinner-border spinner-border-sm mr-1"></span>}
                        Upload
                    </Button>
                </Form>
            </div>
        </>
    )
}

export default UserUploadCV
