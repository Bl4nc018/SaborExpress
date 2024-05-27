import json
import bcrypt
import secrets
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.core.exceptions import PermissionDenied
from .models import User, UserSession


def authenticate_user(request):
    session_token = request.headers.get('SessionToken', None)
    if session_token is None:
        raise PermissionDenied('Unauthorized')
    try:
        user_session = UserSession.objects.get(token=session_token)
    except UserSession.DoesNotExist:
        raise PermissionDenied('Unauthorized')
    return user_session


@csrf_exempt
def user(request):
    if request.method == 'GET':
        try:
            user_session = authenticate_user(request)
            user_id = user_session.user.id
            full_user = User.objects.get(id=user_id)
            json_response = full_user.to_json()
            return JsonResponse(json_response, safe=False, status=200)
        except PermissionDenied:
            return JsonResponse({'error': 'Unauthorized'}, status=401)


@csrf_exempt
def sessions(request):
    if request.method == 'POST':
        try:
            client_json = json.loads(request.body)
            json_username = client_json['username']
            json_password = client_json['password']
        except KeyError:
            return JsonResponse({"response": "not_ok"}, status=400)
        try:
            db_user = User.objects.get(username=json_username)
        except User.DoesNotExist:
            return JsonResponse({"response": "User not in database"}, status=404)
        if bcrypt.checkpw(json_password.encode('utf8'), db_user.password.encode('utf8')):
            random_token = secrets.token_hex(10)
            session = UserSession(user=db_user, token=random_token)
            session.save()
            return JsonResponse({"response": "ok", "SessionToken": random_token}, status=201)
        else:
            return JsonResponse({"response": "Unauthorized"}, status=401)
    elif request.method == 'DELETE':
        try:
            user_session = authenticate_user(request)
        except PermissionDenied:
            return JsonResponse({'response': 'Unauthorized'}, status=401)
        user_session.delete()
        return JsonResponse({"response": "Sesión cerrada"}, status=201, safe=False)
    else:
        return JsonResponse({"response": "HTTP method unsupported"}, status=405)
