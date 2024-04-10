import { Link, Outlet, useParams } from 'react-router-dom';
import * as React from 'react';
import { UserService } from '../../services/User/UserService';
import { GetUserInfoOutputModel } from '../../services/User/models/GetUserInfoOutputModel';
import useUserInfo from '../../hooks/useUserInfo';


function Profile() {
  // Assuming getUserInfo() returns an object that matches GetUserInfoOutputModel
  const { userInfo, isLoading, error } = useUserInfo();

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error: {error.message}</div>;

  return (
    <div>
      <h2>User Detail</h2>
      <div>
        <strong>Name:</strong> {userInfo.name} <Link to="/edit-name">Edit</Link>
      </div>
      <div>
        <strong>Email:</strong> {userInfo.email}
      </div>
      <div>
        <strong>Role:</strong> {userInfo.role}
      </div>
      <div>
        <strong>Credits:</strong> {userInfo.credits}
      </div>
    </div>
  );
}
export default Profile;