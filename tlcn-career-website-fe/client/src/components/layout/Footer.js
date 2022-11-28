import React from 'react'
import '../css/Footer.css'

const Footer = () => {
    let body

    body = (
        <footer className="utew">
            <div className="footer animated fadeIn">
                <div className="footer__container">
                    <div className="columns">
                        <div className="column is-hidden-mobile">
                            <ul id="footer-info" className="footer__link">
                                <li className="footer__link-head">Predict Resume</li>
                                <li>About Predict Resume</li>
                                <li>
                                    <a href="https://www.facebook.com/vanbinh0712">Contact</a>
                                </li>
                                <li>Question and Answer</li>
                                <li>Policy</li>
                            </ul>
                        </div>
                        <div className="column">
                            <ul className="footer__link ">
                                <li className="footer__link-head">For Employer</li>
                                <li>Job Posting</li>
                                <li>Contact</li>
                                <li>Services</li>
                                <li>Search for Profile</li>
                            </ul>
                        </div>
                        <div className="column">
                            <ul className="footer__link ">
                                <li className="footer__link-head">Jobs by region</li>
                                <li>Ho Chi Minh</li>
                                <li>Ha Noi</li>
                                <li>Da Nang</li>
                                <li>Hai Phong</li>
                                <li>Can Tho</li>
                                <li>All Areas</li>
                            </ul>
                        </div>
                        <div className="column">
                            <ul className="footer__link ">
                                <li className="footer__link-head">Jobs by Industry</li>
                                <li>Accountant </li>
                                <li>Bank</li>
                                <li>IT - Software</li>
                                <li>IT - Hardware/Network</li>
                                <li>Build</li>
                                <li>All Industry</li>
                            </ul>
                        </div>
                        <div className="column">
                            <iframe
                                src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d8608.202565779875!2d106.77194854733025!3d10.850817852681455!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752763f23816ab%3A0x282f711441b6916f!2zVHLGsOG7nW5nIMSQ4bqhaSBo4buNYyBTxrAgcGjhuqFtIEvhu7kgdGh14bqtdCBUcC4gSOG7kyBDaMOtIE1pbmg!5e0!3m2!1svi!2s!4v1664989342060!5m2!1svi!2s"
                                width="350"
                                height="300"
                                style={{ border: 0 }}
                                allowFullScreen=""
                                loading="lazy"
                                referrerPolicy="no-referrer-when-downgrade"
                                title="Location"
                            ></iframe>
                        </div>
                    </div>
                    <div style={{ borderTop: '1px solid black ' }}></div>
                </div>
            </div>
        </footer>
    )

    return <>{body}</>
}

export default Footer
