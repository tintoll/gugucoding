import { Link } from "react-router-dom";

const BasicMenu = () => {
  return (
    <nav id="navbar" className="flex bg-blue-300 ">
      <div className="w-4/5 bg-gray-500">
        <ul className="flex p-4 font-bold text-white">
          <li className="pr-6 text-2xl">
            <Link to={"/"}>Main</Link>
          </li>
          <li className="pr-6 text-2xl">
            <Link to={"/about"}>About</Link>
          </li>
          <li className="pr-6 text-2xl">
            <Link to={"/todo/"}>Todo</Link>
          </li>
        </ul>
      </div>

      <div className="flex justify-end w-1/5 p-4 font-medium bg-orange-300">
        <div className="m-1 text-sm text-white rounded">Login</div>
      </div>
    </nav>
  );
};

export default BasicMenu;
