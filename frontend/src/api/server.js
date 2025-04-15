import express from 'express';
import bodyParser from 'body-parser';


// Инициализация приложения
const app = express();
const PORT = 5000;

// Моковые данные для менторов и заявок на менторство
let mentors = [
  { id: 1, name: 'John Doe', email: 'john.doe@example.com', students: [] },
  { id: 2, name: 'Jane Smith', email: 'jane.smith@example.com', students: [] }
];

let mentorshipRequests = [
  // Пример запроса на менторство
  { mentorId: 1, studentName: 'Alice', status: 'pending' }
];

// Мидлвар для парсинга JSON-данных
app.use(bodyParser.json());

// Получить список менторов
app.get('/mentors', (req, res) => {
  res.json(mentors);
});

// Отправить запрос на менторство
app.post('/mentorship-request', (req, res) => {
  const { mentorId, studentName } = req.body;

  if (!mentorId || !studentName) {
    return res.status(400).json({ message: 'Неверные данные запроса' });
  }

  // Добавляем запрос на менторство
  mentorshipRequests.push({ mentorId, studentName, status: 'pending' });

  res.status(201).json({ message: 'Запрос на менторство отправлен' });
});

// Получить запросы на менторство для конкретного ментора
app.get('/mentorship-requests/:mentorId', (req, res) => {
  const { mentorId } = req.params;
  const requests = mentorshipRequests.filter(request => request.mentorId == mentorId);

  res.json(requests);
});

// Подтвердить запрос на менторство
app.post('/accept-mentorship/:mentorId', (req, res) => {
  const { mentorId } = req.params;
  const { studentName } = req.body;

  // Находим запрос на менторство
  const requestIndex = mentorshipRequests.findIndex(
    request => request.mentorId == mentorId && request.studentName == studentName && request.status == 'pending'
  );

  if (requestIndex === -1) {
    return res.status(404).json({ message: 'Запрос не найден или уже подтвержден' });
  }

  // Обновляем статус запроса и добавляем студента в список менторов
  mentorshipRequests[requestIndex].status = 'accepted';
  const mentor = mentors.find(m => m.id == mentorId);
  mentor.students.push(studentName);

  res.json({ message: `Запрос на менторство для ${studentName} принят` });
});

// Отклонить запрос на менторство
app.post('/reject-mentorship/:mentorId', (req, res) => {
  const { mentorId } = req.params;
  const { studentName } = req.body;

  // Находим запрос на менторство
  const requestIndex = mentorshipRequests.findIndex(
    request => request.mentorId == mentorId && request.studentName == studentName && request.status == 'pending'
  );

  if (requestIndex === -1) {
    return res.status(404).json({ message: 'Запрос не найден или уже отклонен/принят' });
  }

  // Обновляем статус запроса
  mentorshipRequests[requestIndex].status = 'rejected';

  res.json({ message: `Запрос на менторство для ${studentName} отклонен` });
});

// Запуск сервера
app.listen(PORT, () => {
  console.log(`Сервер запущен на порту ${PORT}`);
});