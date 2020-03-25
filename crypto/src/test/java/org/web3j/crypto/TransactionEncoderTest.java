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
package org.web3j.crypto;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("deprecation")
public class TransactionEncoderTest {

    @Test
    public void testSignMessage() {
        byte[] signedMessage =
                TransactionEncoder.signMessage(createEtherTransaction(), SampleKeys.CREDENTIALS);
        String hexMessage = Numeric.toHexString(signedMessage);
        assertEquals(
                hexMessage,
                ("0xf85880010a808080840add5355887fffffffffffffff801ba0667df8c27d34288b31bb70a9afdd5c8f05855cad4764d43805e85556db91adf1a03e6254afd997bee82bec6d610aec7ca415b819a6abd653549475f11d31a3d207"));
    }

    @Test
    public void testEtherTransactionAsRlpValues() {
        List<RlpType> rlpStrings =
                TransactionEncoder.asRlpValues(
                        createEtherTransaction(),
                        new Sign.SignatureData((byte) 0, new byte[32], new byte[32]));
        assertEquals(rlpStrings.size(), (12));
        assertEquals(rlpStrings.get(6), (RlpString.create(new BigInteger("add5355", 16))));
    }

    @Test
    public void testContractAsRlpValues() {
        List<RlpType> rlpStrings =
                TransactionEncoder.asRlpValues(createContractTransaction(), null);
        assertEquals(rlpStrings.size(), (9));
        assertEquals(rlpStrings.get(6), (RlpString.create("")));
    }

    @Test
    public void testEip155Encode() {
        assertArrayEquals(
                TransactionEncoder.encode(createEip155RawTransaction(), (byte) 1),
                (Numeric.hexStringToByteArray(
                        "0xef098504a817c800825208808080943535353535353535353535353535353535353535880de0b6b3a764000080018080")));
    }

    @Test
    public void testEip155Transaction() {
        // https://github.com/ethereum/EIPs/issues/155
        Credentials credentials =
                Credentials.create(
                        "0x4646464646464646464646464646464646464646464646464646464646464646");

        assertArrayEquals(
                TransactionEncoder.signMessage(createEip155RawTransaction(), (byte) 1, credentials),
                (Numeric.hexStringToByteArray(
                        "0xf86f098504a817c800825208808080943535353535353535353535353535353535353535880de0b6b3a76400008025a022f12ae0c33759d4fa82c145de69ec907850cede6722a3bf3b7b69d78a5b0a1ba02d12f090c4d8c636677e0cb3619938294001b528b49b9ea16d32502227e8f5ee")));
    }

    private static RawTransaction createEtherTransaction() {
        return RawTransaction.createEtherTransaction(
                BigInteger.ZERO,
                BigInteger.ONE,
                BigInteger.TEN,
                "0xadd5355",
                BigInteger.valueOf(Long.MAX_VALUE));
    }

    static RawTransaction createContractTransaction() {
        return RawTransaction.createContractTransaction(
                BigInteger.ZERO,
                BigInteger.ONE,
                BigInteger.TEN,
                BigInteger.valueOf(Long.MAX_VALUE),
                "01234566789");
    }

    private static RawTransaction createEip155RawTransaction() {
        return RawTransaction.createEtherTransaction(
                BigInteger.valueOf(9),
                BigInteger.valueOf(20000000000L),
                BigInteger.valueOf(21000),
                "0x3535353535353535353535353535353535353535",
                BigInteger.valueOf(1000000000000000000L));
    }
}
