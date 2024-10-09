import tensorflow as tf

# Keras HDF5 모델 로드
model = tf.keras.models.load_model('tomato_test/model.h5')

# TensorFlow Lite로 변환
converter = tf.lite.TFLiteConverter.from_keras_model(model)
tflite_model = converter.convert()

# .tflite 파일로 저장
with open('tomato_test/model.tflite', 'wb') as f:
    f.write(tflite_model)
