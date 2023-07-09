import { Outlet } from "react-router-dom"
import Footer from "../Footer/Footer";

function FooterLayout() {
  return (
    <>
      <Outlet />
      <Footer />
    </>
  );
}

export default FooterLayout;