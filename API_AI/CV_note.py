from pydantic import BaseModel
class CV_note (BaseModel):
    variance: float 
    skewness: float 
    curtosis: float 
    entropy: float