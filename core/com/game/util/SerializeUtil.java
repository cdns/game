package com.game.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.game.IMsgBody;


public class SerializeUtil {
    private static Logger log = Logger.getLogger(SerializeUtil.class);
    private static final byte[] _$2 = "0123456789ABCDEF".getBytes();

    public static byte[] intToByte(int paramInt) {
        byte[] arrayOfByte = new byte[4];
        arrayOfByte[0] = (byte) (0xFF & paramInt);
        arrayOfByte[1] = (byte) ((0xFF00 & paramInt) >> 8);
        arrayOfByte[2] = (byte) ((0xFF0000 & paramInt) >> 16);
        arrayOfByte[3] = (byte) ((0xFF000000 & paramInt) >> 24);
        return arrayOfByte;
    }

    public static int byteToInt(byte[] paramArrayOfByte) {
        return (((paramArrayOfByte[0] & 0xFF) << 8 | paramArrayOfByte[1] & 0xFF) << 8 | paramArrayOfByte[2] & 0xFF) << 8 | paramArrayOfByte[3] & 0xFF;
    }

    public static char byteToChar(byte paramByte) {
        return (char) paramByte;
    }

    private static int charToInt(char paramChar) {
        if (paramChar >= 'a')
            return paramChar - 'a' + 10 & 0xF;
        if (paramChar >= 'A')
            return paramChar - 'A' + 10 & 0xF;
        return paramChar - '0' & 0xF;
    }

    public static String byteToHexString(byte[] bytes) {
        byte[] arrayOfByte = new byte[2 * bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            arrayOfByte[(2 * i)] = _$2[(bytes[i] >> 4 & 0xF)];
            arrayOfByte[(2 * i + 1)] = _$2[(bytes[i] & 0xF)];
        }
        return new String(arrayOfByte);
    }

    public static byte[] HexStringToByte(String paramString) {
        byte[] arrayOfByte = new byte[paramString.length() / 2];
        int i = 0;
        for (int j = 0; j < arrayOfByte.length; j++) {
            char c1 = paramString.charAt(i++);
            char c2 = paramString.charAt(i++);
            arrayOfByte[j] = (byte) (charToInt(c1) << 4 | charToInt(c2));
        }
        return arrayOfByte;
    }

    public static String byteToString(byte[] paramArrayOfByte) {
        StringBuilder localStringBuilder = new StringBuilder();
        for (int i = 0; i < paramArrayOfByte.length; i++)
            localStringBuilder.append((char) paramArrayOfByte[i]);
        return localStringBuilder.toString();
    }

    public static byte[] objectToByte(Object paramObject) {
        if (null == paramObject) return null;
        ObjectOutputStream localObjectOutputStream = null;
        ByteArrayOutputStream localByteArrayOutputStream = null;
        byte[] arrayOfByte = null;
        try {
            localByteArrayOutputStream = new ByteArrayOutputStream();
            localObjectOutputStream = new ObjectOutputStream(localByteArrayOutputStream);
            localObjectOutputStream.writeObject(paramObject);
            localObjectOutputStream.flush();
            localByteArrayOutputStream.flush();
            arrayOfByte = localByteArrayOutputStream.toByteArray();
            return arrayOfByte;
        }catch (IOException e) {
            e.printStackTrace();
            log.error(e.toString());
            arrayOfByte = null;
            return arrayOfByte;
        }finally {
            try {
                if (localByteArrayOutputStream != null) {
                    localByteArrayOutputStream.reset();
                    localByteArrayOutputStream.close();
                    localByteArrayOutputStream = null;
                }
                if (localObjectOutputStream != null) {
                    localObjectOutputStream.reset();
                    localObjectOutputStream.close();
                    localObjectOutputStream = null;
                }
            }
            catch (IOException localIOException4) {
                localIOException4.printStackTrace();
                log.error(localIOException4.toString());
            }
        }
    }

    public static String objectToStringByJson(Object paramObject) {
        try {
            JSONObject localJSONObject = JSONObject.fromObject(paramObject);
            return  localJSONObject.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
    }

    public static Object stringToObjectByJson(String paramString, Class<?> paramClass) {
        Object localObject = null;
        try {
            JSONObject localJSONObject = JSONObject.fromObject(paramString);
            localObject = JSONObject.toBean(localJSONObject, paramClass);
        }
        catch (Exception localException) {
            localException.printStackTrace();
            log.error(localException.toString());
        }
        return localObject;
    }

    public static byte[] byteCompress(byte[] paramArrayOfByte) {
        Deflater localDeflater = new Deflater();
        localDeflater.reset();
        localDeflater.setLevel(9);
        localDeflater.setInput(paramArrayOfByte);
        localDeflater.finish();
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(paramArrayOfByte.length);
        byte[] arrayOfByte1 = new byte[1024];
        while (!localDeflater.finished()) {
            int i = localDeflater.deflate(arrayOfByte1);
            localByteArrayOutputStream.write(arrayOfByte1, 0, i);
        }
        byte[] arrayOfByte2 = null;
        try {
            arrayOfByte2 = localByteArrayOutputStream.toByteArray();
            localDeflater.end();
            localDeflater = null;
            if (localByteArrayOutputStream != null) {
                localByteArrayOutputStream.close();
                localByteArrayOutputStream = null;
            }
        }
        catch (IOException localIOException) {
            localIOException.printStackTrace();
            log.error(localIOException.toString());
        }
        return arrayOfByte2;
    }

    public static byte[] byteUncompress(byte[] paramArrayOfByte) {
        if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))return null;
        Inflater localInflater = new Inflater();
        localInflater.reset();
        localInflater.setInput(paramArrayOfByte);
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(paramArrayOfByte.length);
        try {
            byte[] arrayOfByte2 = new byte[1024];
            while (!localInflater.finished()) {
                int i = localInflater.inflate(arrayOfByte2);
                localByteArrayOutputStream.write(arrayOfByte2, 0, i);
            }
            return localByteArrayOutputStream.toByteArray();
        }catch (Exception e) {
            e.printStackTrace();
            return paramArrayOfByte;
        }
        finally {
            localInflater.end();
            try {
                localByteArrayOutputStream.close();
            }
            catch (IOException localIOException3) {
                localIOException3.printStackTrace();
            }
        }
    }

    public static Object byteToObject(byte[] paramArrayOfByte) {
        if (paramArrayOfByte == null)return null;
        ObjectInputStream localObjectInputStream = null;
        ByteArrayInputStream localByteArrayInputStream = null;
        try {
            localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
            localObjectInputStream = new ObjectInputStream(localByteArrayInputStream);
            return localObjectInputStream.readObject();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }catch (IOException e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        } finally {
            try {
                if (localByteArrayInputStream != null) {
                    localByteArrayInputStream.reset();
                    localByteArrayInputStream.close();
                    localByteArrayInputStream = null;
                }
                if (localObjectInputStream != null) {
                    localObjectInputStream.close();
                    localObjectInputStream = null;
                }
            }
            catch (IOException localIOException5) {
                localIOException5.printStackTrace();
                log.error(localIOException5.toString());
            }
        }
    }

    
    public static byte[] msgBodyToByte(IMsgBody paramIMsgBody) {
        if (paramIMsgBody == null) return null;
        ByteArrayOutputStream localByteArrayOutputStream = null;
        DataOutputStream localDataOutputStream = null;
        byte[] arrayOfByte = null;
        try {
            localByteArrayOutputStream = new ByteArrayOutputStream();
            localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
            paramIMsgBody.write(localDataOutputStream);
            localDataOutputStream.flush();
            localByteArrayOutputStream.flush();
            arrayOfByte = localByteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (localByteArrayOutputStream != null) {
                    localByteArrayOutputStream.reset();
                    localByteArrayOutputStream.close();
                }
                if (localDataOutputStream != null) {
                    localDataOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return arrayOfByte;
    }
   
    public static void byteToObject(byte[] paramArrayOfByte, IMsgBody paramIMsgBody) {
        ByteArrayInputStream localByteArrayInputStream = null;
        DataInputStream localDataInputStream = null;
        try {
            localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
            localDataInputStream = new DataInputStream(localByteArrayInputStream);
            paramIMsgBody.read(localDataInputStream);
        } catch (Exception e) {
            log.info(e.toString());
        } finally {
            try {
                if (localDataInputStream != null) {
                    localDataInputStream.reset();
                    localDataInputStream.close();
                    localDataInputStream = null;
                }
                if (localByteArrayInputStream != null) {
                    localByteArrayInputStream.reset();
                    localByteArrayInputStream.close();
                    localByteArrayInputStream = null;
                }
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        }
    }

}
