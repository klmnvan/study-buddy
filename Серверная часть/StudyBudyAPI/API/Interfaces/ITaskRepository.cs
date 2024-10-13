﻿

namespace StudyBudyAPI.Interfaces
{
    public interface ITaskRepository
    {
        public bool TaskIsExists(int? id);
        public List<Models.DB.Task> GetTaskListUser(Guid idUser); //Получение списка задач пользователя
        public Models.DB.Task AddTask(Models.DB.Task newTask); //Добавление задачи

    }
}
