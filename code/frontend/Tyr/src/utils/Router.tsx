import { createBrowserRouter, Link, Outlet, useParams } from 'react-router-dom';
import Layout from '../components/Layout';
import Home from '../pages/Home/Home';
import * as React from 'react';
import Profile from '../pages/Profile/Profile';

export const router = createBrowserRouter([
  {
    "path": "/", "element": <Layout />, "children": [
      {
        "path": "/",
        "element": <Home />,
      },
      {
        "path": "/profile",
        "element": <Profile />
      },
      {
        "path": "/path2",
        "element": <Screen2 />
      },
      {
        "path": "/path3",
        "element": <Screen3 />
      },
    ]
  }
])




function Authors() {
  return (
    <div>
      Authors:
      <ul>
        <li>Alice</li>
        <li>Bob</li>
      </ul>
    </div>
  )
}

function Screen1() {
  return (
    <div>
      <h1>Screen 1</h1>
    </div>
  )
}

function Screen2() {
  return (
    <div>
      <h1>Screen 2</h1>
    </div>
  )
}

function Screen3() {
  return (
    <div>
      <h1>Screen 3</h1>
    </div>
  )
}

function UserDetail() {
  const { uid } = useParams()
  return (
    <div>
      <h2>User Detail</h2>
      {uid}
      <p><Link to="latest">Latest Games</Link></p>
      <p><Link to="stats">Statistics</Link></p>
      <Outlet />
    </div>
  )
}