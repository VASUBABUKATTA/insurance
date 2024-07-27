import React, { useEffect, useState } from 'react';
import PropertyInsuranceService from '../Service/PropertyInsuranceService';

function AdminNotificationspage() {
    const [detailsForEmail, setDetailsForEmail] = useState([]);
    const [detailsForMobile, setDetailsForMobile] = useState([]);

    const [mobileData,setmobileData,] = useState("");

    const [EmailData,setEmailData,] = useState("");

    useEffect(() => {
        PropertyInsuranceService.getAllDetailsOfMobile()
            .then((res) => {
                setDetailsForMobile(res.data);
            })
            .catch((error) => console.error('Error fetching mobile details:', error));

        PropertyInsuranceService.getAllDetailsofEmail()
            .then((res) => {
                setDetailsForEmail(res.data);
            })
            .catch((error) => console.error('Error fetching email details:', error));
    }, []);

    const filteredDetailsForEmail = detailsForEmail.filter(detail => detail.status === 'request');
    const filteredDetailsForMobile = detailsForMobile.filter(detail => detail.status === 'request');

    const handleClickEmail=(customerId,statusValue)=>
    {
            console.log(customerId,statusValue);
            PropertyInsuranceService.getAllDetailsofEmailFindByCustomerId(customerId)
            .then((res) => {
                setEmailData(res.data);
                console.log(res.data.email);
                const data=
                {
                    status :statusValue,
                    email : res.data.email
                }
                console.log(customerId,statusValue,data); 
                PropertyInsuranceService.upDateStatusForEmail(customerId,data)
                .then((res) => {
                    setmobileData(res.data);
                    console.log(res.data)
                })
                .catch((error) => console.error('Error fetching  details:', error));
    
            })
            .catch((error) => console.error('Error fetching  details:', error));

           
            
    }

    const handleClickMobile=(customerId,statusValue)=>
        {
            console.log(customerId);
            
            PropertyInsuranceService.getAllDetailsOfMobileFindByCustomerId(customerId)
            .then((res) => {
                setmobileData(res.data);
                console.log(res.data)
                const data=
                {
                    status :statusValue,
                    mobileNo : res.data.mobileNo
                }
                console.log(customerId,statusValue,data); 
                PropertyInsuranceService.upDateStatusForMobile(customerId,data)
                .then((res) => {
                    setmobileData(res.data);
                    console.log(res.data)
                })
                .catch((error) => console.error('Error fetching  details:', error));
            })
            .catch((error) => console.error('Error fetching email details:', error));
        }
    
    return (
        <div className='container-fluid'>
          <div className='mx-4 mt-3'>
            <h3 className='text-center bg-primary text-light rounded fw-bold'>Notifications <i className="fa-solid fa-bell ms-2"></i></h3>
            <div className="container mt-4">
                <h4 className='my-1 text-secondary fw-bold'>Email Notifications :</h4>
                <table className='table table-striped-columns table-bordered table-hover border-dark mt-lg-4'>
                    <thead className='text-center'>
                        <tr>
                            <th className='text-primary'>ID</th>
                            <th className='text-primary'>Customer ID</th>
                            <th className='text-primary'>Email</th>
                            <th className='text-primary'>Status</th>
                            <th className='text-primary'>Date</th>
                            <th className='text-primary'>Actions</th>
                        </tr>
                    </thead>
                    <tbody className='text-center'>
                        {filteredDetailsForEmail.map((detail) => (
                            <tr key={detail.id}>
                                <td>{detail.id}</td>
                                <td>{detail.customerId || 'N/A'}</td>
                                <td>{detail.email}</td>
                                <td>{detail.status}</td>
                                <td>{new Date(detail.date).toLocaleString()}</td>
                                <td className='mx-2 my-2'>
                                    <button className='btn-link bg-primary' onClick={() => handleClickEmail(detail.customerId,"approved")} >Approved</button>
                                    <button  className='btn-link bg-danger' onClick={() => handleClickEmail(detail.customerId,"rejected")}>Rejected</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>

                <h4 className='my-1 text-secondary fw-bold'>Mobile Notifications :</h4>
                <table className='table table-striped-columns table-bordered table-hover border-dark mt-lg-4'>
                    <thead className='text-center'>
                        <tr>
                            <th className='text-primary'>ID</th>
                            <th className='text-primary'>Customer ID</th>
                            <th className='text-primary'>Mobile Number</th>
                            <th className='text-primary'>Status</th>
                            <th className='text-primary'>Date</th>
                            <th className='text-primary'>Actions</th>
                        </tr>
                    </thead>
                    <tbody className='text-center'>
                        {filteredDetailsForMobile.map((detail) => (
                            <tr key={detail.id}>
                                <td>{detail.id}</td>
                                <td>{detail.customerId}</td>
                                <td>{detail.mobileNo}</td>
                                <td>{detail.status}</td>
                                <td>{new Date(detail.date).toLocaleString()}</td>
                                <td className='mx-2 my-2'>
                                    <button className='btn-lin bg-primary'  onClick={()=>handleClickMobile(detail.customerId,"approved")}>Approved</button>
                                    <button className='btn-lin bg-danger'  onClick={()=>handleClickMobile(detail.customerId,"rejected")}>Rejected</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            </div>
        </div>
    );
}

export default AdminNotificationspage;