import { useEffect, useState } from "react";
import { getOne } from "../../api/todoApi";
const initState = {
  tno: 0,
  title: "",
  writer: "",
  dueDate: null,
  complete: false,
};
const ReadComponent = ({ tno }) => {
  const [todo, setTodo] = useState(initState); //아직 todo는 사용하지 않음
  useEffect(() => {
    getOne(tno).then((data) => {
      console.log(data);
      setTodo(data);
    });
  }, [tno]);
  return (
    <div className="p-4 m-2 mt-10 border-2 border-sky-200">
      {makeDiv("Tno", todo.tno)}
      {makeDiv("Writer", todo.writer)}
      {makeDiv("Title", todo.title)}
      {makeDiv("Title", todo.complete ? "Completed" : "Not Yet")}
    </div>
  );
};
const makeDiv = (title, value) => (
  <div className="flex justify-center">
    <div className="relative flex flex-wrap items-stretch w-full mb-4">
      <div className="w-1/5 p-6 font-bold text-right">{title}</div>
      <div className="w-4/5 p-6 border border-solid rounded-r shadow-md">
        {value}
      </div>
    </div>
  </div>
);

export default ReadComponent;
