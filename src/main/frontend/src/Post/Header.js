import React from 'react';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap/dist/css/bootstrap.css';
import { Nav, Navbar, NavDropdown, Button } from 'react-bootstrap';
import { Link } from "react-router-dom";

const Header = () => {
  return (
    <header id="header" className="header fixed-top" data-scrollto-offset="0">
          <div className="container-fluid d-flex align-items-center justify-content-between">
            <a href="/" className="logo d-flex align-items-center scrollto me-auto me-lg-0">
              <h1>Softalk<span>.</span></h1>
            </a>

            <nav id="navbar" className="navbar">
              <ul>
                <li><a href="/">Menu 1</a></li>
                <li><a href="/">Menu 2</a></li>
              </ul>
              <i className="bi bi-list mobile-nav-toggle d-none"></i>
            </nav>
            <a className="btn-getstarted scrollto" href="/">Get Started</a>
          </div>
    </header>
  );
}

export default Header;
