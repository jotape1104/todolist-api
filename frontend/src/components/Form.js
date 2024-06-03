import React, { useRef, useEffect } from "react";
import styled from "styled-components";
import axios from "axios";
import { toast } from "react-toastify";


const FormContainer = styled.form`
    width: 350px;
    height: 75px;
    padding: 10px;
    background-color: rgba(255,255,255,0.3);
    border: 2px solid white;
    border-radius: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 20px;
    box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
`;

const InputArea = styled.div`
    width: 250px;
    display: flex;
    flex-direction: column;
    align-items: start;
`;

const Input = styled.input`
    width: 100%;
    height: 35px;
    padding: 0 10px;
    border: 1px solid #bbb;
    border-radius: 5px;
    font-size: 1rem;
    
`;

const Label = styled.label``;

const Button = styled.button`
    height: 40px;
    width: 40px;
    padding-top: 1px;
    cursor: pointer;
    border-radius: 5px;
    border: none;
    background-color: #233e54;
    box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
    color: white;
    font-size: 1.5rem;
    display: flex;
    align-items: center;
    justify-content: center;
`;


const Form = ({ onEdit, setOnEdit, getTasks }) => {
    const ref = useRef();

    useEffect(() => {
        if(onEdit) {
            const task = ref.current

            task.taskName.value = onEdit.taskName
        }
    }, [onEdit]);

    const handleSubmit = async (e) => {
        e.preventDefault();
    
        const task = ref.current;
    
        if (
          !task.taskName.value 
        ) {
          return toast.warn("Preencha todos os campos!");
        }
    
        if (onEdit) {
          await axios
            .put("http://localhost:8080/todolist/a3/tasks/" + onEdit.id, {
                taskName: task.taskName.value,
            })
            .then(({ data }) => toast.success(data))
            .catch(({ data }) => toast.error(data));
        } else {
          await axios
            .post("http://localhost:8080/todolist/a3/tasks", {
                taskName: task.taskName.value,
            })
            .then(({ data }) => toast.success(data))
            .catch(({ data }) => toast.error(data));
        }
    
        task.taskName.value = "";
    
        setOnEdit(null);
        getTasks();
      };



    return (
        <FormContainer ref={ref} onSubmit={handleSubmit}>
            <InputArea>
                <Label></Label>
                <Input name="taskName" />
            </InputArea>
            <Button type="submit"><i class="bi bi-caret-right"></i></Button>
        </FormContainer>
    )
}

export default Form;