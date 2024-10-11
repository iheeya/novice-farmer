from fastapi import APIRouter
from setting.schemas import FarmRequestSchema
from todo.fert import update_request_todo

router = APIRouter()

@router.post('/plant/todo')
async def update_todo(request: FarmRequestSchema):
    farm_id = request.farm_id
    todo_type = request.farm_todo_type
    
    update_request_todo(farm_id, todo_type)
    return {"message:": "Success!"}