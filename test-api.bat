@echo off
echo Testing Bookstore API...
echo.
echo 1. Testing health endpoint...
curl -s http://localhost:8080/actuator/health
echo.
echo.
echo 2. Testing books endpoint...
curl -s http://localhost:8080/api/books
echo.
echo.
echo 3. Testing register endpoint...
curl -X POST -H "Content-Type: application/json" -d "{\"username\":\"testuser\",\"email\":\"test@test.com\",\"password\":\"test123\"}" http://localhost:8080/api/auth/register
echo.
echo.
pause
