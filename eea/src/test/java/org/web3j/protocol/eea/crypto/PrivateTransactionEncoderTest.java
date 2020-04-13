/*
 * Copyright 2019 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.web3j.protocol.eea.crypto;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.web3j.crypto.Credentials;
import org.web3j.utils.Base64String;
import org.web3j.utils.Numeric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.web3j.utils.Restriction.RESTRICTED;

public class PrivateTransactionEncoderTest {

    private static final Base64String MOCK_ENCLAVE_KEY =
            Base64String.wrap("A1aVtMxLCUHmBVHXoZzzBgPbW/wj5axDpW9X8l91SGo=");
    private static final Base64String MOCK_ENCLAVE_KEY_2 =
            Base64String.wrap("Ko2bVqD+nNlNYL5EE7y3IdOnviftjiizpjRt+HTuFBs=");
    private static final Base64String MOCK_PRIVACY_GROUP_ID =
            Base64String.wrap("DyAOiF/ynpc+JXa2YAGB0bCitSlOMNm+ShmB/7M6C4w=");
    private static final List<Base64String> MOCK_PRIVATE_FOR =
            Arrays.asList(MOCK_ENCLAVE_KEY, MOCK_ENCLAVE_KEY_2);

    @Test
    public void testSignLegacyTransaction() {
        final String expected =
                "0xf8d7808203e8832dc6c080808094627306090abab3a6e1400e9345bc60c78a8bef578080820fe7a0f1e5957f0d67e3c8cd3f8ed88ce8db95ab50f132ccbe0129f299959d7d0a407da00916873494aed8552c06f9bc1a095d1a496091cdc9376cf058f229ca13f02331a0035695b4cc4b0941e60551d7a19cf30603db5bfc23e5ac43a56f57f25f75486af842a0035695b4cc4b0941e60551d7a19cf30603db5bfc23e5ac43a56f57f25f75486aa02a8d9b56a0fe9cd94d60be4413bcb721d3a7be27ed8e28b3a6346df874ee141b8a72657374726963746564";
        final RawPrivateTransaction privateTransactionCreation =
                new RawPrivateTransaction(
                        BigInteger.ZERO,
                        BigInteger.valueOf(1000),
                        BigInteger.valueOf(3000000),
                        "0x627306090abab3a6e1400e9345bc60c78a8bef57",
                        "0x",
                        MOCK_ENCLAVE_KEY,
                        MOCK_PRIVATE_FOR,
                        null,
                        RESTRICTED);
        final long chainId = 2018;
        final String privateKey =
                "8f2a55949038a9610f50fb23b5883af3b4ecb3c3bb792cbcefbd1542c692be63";
        final Credentials credentials = Credentials.create(privateKey);
        final String privateRawTransaction =
                Numeric.toHexString(
                        PrivateTransactionEncoder.signMessage(
                                privateTransactionCreation, chainId, credentials));

        assertEquals(expected, privateRawTransaction);
    }

    @Test
    public void testSignBesuTransaction() {
        final String expected =
                "0xf8b4808203e8832dc6c080808094627306090abab3a6e1400e9345bc60c78a8bef578080820fe7a0d98d0b2c327b7685235574117a2fb3fd95804ff9cc3930d2a04955fcea8bedb0a05d5312f73307879e5a78f3bdde1ea26e5c1f00df3208414b2cd6d101c8b2ce91a0035695b4cc4b0941e60551d7a19cf30603db5bfc23e5ac43a56f57f25f75486aa00f200e885ff29e973e2576b6600181d1b0a2b5294e30d9be4a1981ffb33a0b8c8a72657374726963746564";
        final RawPrivateTransaction privateTransactionCreation =
                new RawPrivateTransaction(
                        BigInteger.ZERO,
                        BigInteger.valueOf(1000),
                        BigInteger.valueOf(3000000),
                        "0x627306090abab3a6e1400e9345bc60c78a8bef57",
                        "0x",
                        MOCK_ENCLAVE_KEY,
                        null,
                        MOCK_PRIVACY_GROUP_ID,
                        RESTRICTED);
        final long chainId = 2018;
        final String privateKey =
                "8f2a55949038a9610f50fb23b5883af3b4ecb3c3bb792cbcefbd1542c692be63";
        final Credentials credentials = Credentials.create(privateKey);
        final String privateRawTransaction =
                Numeric.toHexString(
                        PrivateTransactionEncoder.signMessage(
                                privateTransactionCreation, chainId, credentials));

        assertEquals(expected, privateRawTransaction);
    }
}
