from fastapi import FastAPI
from pydantic import BaseModel
from sentence_transformers import SentenceTransformer, util

app = FastAPI()

# Грузится один раз при старте — ~120mb, потом в памяти
model = SentenceTransformer("paraphrase-multilingual-MiniLM-L12-v2")

class CheckRequest(BaseModel):
    correct_answer: str
    user_answer: str
    threshold: float = 0.75

class CheckResponse(BaseModel):
    correct: bool
    similarity: float

@app.post("/check-answer", response_model=CheckResponse)
async def check_answer(req: CheckRequest):
    # Быстрый точный матч — без модели вообще
    if req.correct_answer.strip().lower() == req.user_answer.strip().lower():
        return CheckResponse(correct=True, similarity=1.0)

    embeddings = model.encode(
        [req.correct_answer, req.user_answer],
        convert_to_tensor=True
    )
    similarity = float(util.cos_sim(embeddings[0], embeddings[1]))

    return CheckResponse(
        correct=similarity >= req.threshold,
        similarity=round(similarity, 3)
    )
