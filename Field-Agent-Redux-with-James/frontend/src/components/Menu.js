import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { LinkContainer } from 'react-router-bootstrap'

function Menu() {
  return (
    <Navbar bg="light" variant="light">
      <Container>
        <Navbar.Brand href="#home">Field Agent Deluxe</Navbar.Brand>
        <Nav className="me-auto">
          <LinkContainer to="/"><Nav.Link>Home</Nav.Link></LinkContainer>
          <LinkContainer to="/agencies"><Nav.Link>Agencies</Nav.Link></LinkContainer>
          <LinkContainer to="/agents"><Nav.Link>Agents</Nav.Link></LinkContainer>
          <LinkContainer to="/securityclearances"><Nav.Link>Security Clearances</Nav.Link></LinkContainer>
        </Nav>
      </Container>
    </Navbar>
  );
}

export default Menu;